/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.users;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.incoresoft.Utils.MD5;
import com.incoresoft.Utils.SIDGenerator;
import com.incoresoft.dto.ldap.LDAPImportParams;
import com.incoresoft.dto.ldap.LDAPUserData;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.sorting.SortFields;
import com.incoresoft.plugin.commons.sorting.SortOrders;
import com.incoresoft.plugin.commons.sorting.SortParams;
import com.incoresoft.sessions.SessionHandlerImpl;
import com.incoresoft.sessions.api.ApiToken;
import com.incoresoft.sessions.api.ApiTokenManager;
import com.incoresoft.vezha.auth.AuthUserRetriever;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.database.UsersRepository;
import com.incoresoft.vezha.models.users.UserImpl;
import com.incoresoft.vezha.models.users.exceptions.CannotCreateUserWithRootRoleException;
import com.incoresoft.vezha.models.users.exceptions.CannotDeleteRootUserException;
import com.incoresoft.vezha.models.users.exceptions.CannotEditRootUserException;
import com.incoresoft.vezha.models.users.exceptions.UserCannotDeleteItselfException;
import com.incoresoft.vezha.models.users.exceptions.UserWithThisEmailAlreadyExistsException;
import com.incoresoft.vezha.models.users.exceptions.WrongLoginOrPasswordException;
import com.incoresoft.vezha.models.users.ldap.VezhaLdapClient;
import com.incoresoft.vezha.models.users.model.UserSearchParams;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.websocket.WsContext;
import jakarta.servlet.http.HttpSession;
import java.lang.runtime.SwitchBootstraps;
import java.time.Duration;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.NotNull;

public class UsersManagerImpl
implements UsersManager {
    private final Map<Integer, UserImpl> data = new ConcurrentHashMap<Integer, UserImpl>();
    private final Map<Integer, UserImpl> archivedUsers = new ConcurrentHashMap<Integer, UserImpl>();
    private final UsersRepository repository;
    private final RolesManager roleManager;
    private final ApiTokenManager apiTokenManager;
    private final SessionHandlerImpl sessionHandler;
    private final AuthUserRetriever authUserRetriever;
    private final VezhaConfigManager configManager;
    private final Provider<Auditor> auditor;
    private final Provider<VezhaLdapClient> ldapClient;

    public void init() {
        this.data.putAll(this.repository.getUsers().stream().collect(Collectors.toMap(UserImpl::getId, Function.identity())));
        this.archivedUsers.putAll(this.repository.getArchivedUsers().stream().collect(Collectors.toMap(UserImpl::getId, Function.identity())));
    }

    public Map<Integer, UserImpl> getArchivedUsers() {
        return Collections.unmodifiableMap(this.archivedUsers);
    }

    public User login(Context ctx, String login, String password) {
        boolean correctPasswordProvided;
        UserImpl user = this.getData().values().stream().filter(it -> it.getStatus() == 1 && it.getEmail().equals(login)).findFirst().orElse(null);
        if (user == null) {
            throw new WrongLoginOrPasswordException();
        }
        switch (user.getType()) {
            case "ldap": {
                boolean bl = this.ldapClient.get().authorize(user, password).success();
                break;
            }
            case "basic": {
                boolean bl = user.checkPassword(password);
                break;
            }
            default: {
                boolean bl = correctPasswordProvided = false;
            }
        }
        if (!correctPasswordProvided) {
            throw new WrongLoginOrPasswordException();
        }
        this.updateLastLogin(user.getId(), ctx.ip());
        ctx.sessionAttribute("user_id", user.getId());
        ctx.sessionAttribute("client_id", user.getClientId());
        return user;
    }

    public void isRootOrThrow(Context ctx) {
        Optional.ofNullable(ctx).map(this::getUser).filter(User::isRoot).orElseThrow(ForbiddenResponse::new);
    }

    public List<UserImpl> getUsers(@NotNull UserImpl user, @NotNull UserSearchParams params, SortParams sortParams) {
        Stream<UserImpl> userStream = this.data.values().stream().filter(it -> it.getStatus() != -1).filter(user::hasAccessTo);
        if (!TextUtils.isEmpty(params.getName())) {
            String name = params.getName().strip();
            userStream = userStream.filter(it -> it.getFullname().contains(name) || it.getEmail().contains(name));
        }
        if (params.getRoleIds() != null && !params.getRoleIds().isEmpty()) {
            userStream = userStream.filter(it -> it.getRoleIds().stream().anyMatch(params.getRoleIds()::contains));
        }
        Enum enum_ = sortParams.getSortField();
        int n = 0;
        Comparator<UserImpl> comparator = switch (SwitchBootstraps.enumSwitch("enumSwitch", new Object[]{"ID", "NAME", "CREATION_TIME"}, (SortFields)enum_, n)) {
            default -> throw new MatchException(null, null);
            case 0 -> Comparator.comparing(UserImpl::getId);
            case 1 -> Comparator.comparing(UserImpl::getEmail, String.CASE_INSENSITIVE_ORDER);
            case 2 -> Comparator.comparing(UserImpl::getCreatedAt);
            case -1 -> Comparator.comparing(UserImpl::getId).reversed();
        };
        enum_ = sortParams.getSortOrder();
        n = 0;
        comparator = switch (SwitchBootstraps.enumSwitch("enumSwitch", new Object[]{"ASC", "DESC"}, (SortOrders)enum_, n)) {
            default -> throw new MatchException(null, null);
            case 0 -> comparator;
            case 1 -> comparator.reversed();
            case -1 -> comparator;
        };
        return userStream.sorted(comparator).toList();
    }

    public UserImpl addUser(UserImpl user) {
        if (this.exists(user.getEmail())) {
            throw new UserWithThisEmailAlreadyExistsException();
        }
        if (!this.configManager.isVmsIntegrationEnabled() && user.isRoot()) {
            throw new CannotCreateUserWithRootRoleException();
        }
        this.repository.insert(user);
        this.data.put(user.getId(), user);
        return user;
    }

    public UserImpl addLdapUser(LDAPImportParams importParams, LDAPUserData userData, int clientId) {
        if (this.exists(userData.email())) {
            throw new UserWithThisEmailAlreadyExistsException();
        }
        Set<Integer> roleIds = importParams.importParams().roleIds().stream().map(this.roleManager::get).filter(Objects::nonNull).map(Role::getId).collect(Collectors.toSet());
        UserImpl user = new UserImpl(userData, roleIds, this.roleManager, importParams.connectionParams(), clientId);
        this.repository.insert(user);
        this.data.put(user.getId(), user);
        return user;
    }

    public UserImpl updateLdapUser(UserImpl userToUpdate, String newLogin) {
        if (this.exists(newLogin)) {
            throw new UserWithThisEmailAlreadyExistsException();
        }
        userToUpdate.setEmail(newLogin);
        this.repository.update(userToUpdate);
        return userToUpdate;
    }

    public void updateLastLogin(int id, String clientIp) {
        this.repository.updateLastLogin(id, clientIp);
    }

    public void deleteUser(HttpSession currentSession, User user, int userID) {
        UserImpl item = this.data.get(userID);
        if (UsersManagerImpl.isOriginalRoot(item)) {
            throw new CannotDeleteRootUserException();
        }
        if (userID == user.getId()) {
            throw new UserCannotDeleteItselfException();
        }
        item.archive();
        this.data.remove(item.getId());
        this.archivedUsers.put(item.getId(), item);
        this.repository.archiveUser(item.getId());
        this.sessionHandler.invalidateUserSessions(currentSession, item.getId());
        this.revokeAllApiTokens(item);
    }

    public User updateUser(UserImpl user) {
        this.repository.update(user);
        this.data.put(user.getId(), user);
        return this.get(user.getId());
    }

    public User setUserStatus(int userId, int status) {
        UserImpl item = this.data.get(userId);
        if (item.isRoot()) {
            throw new CannotEditRootUserException();
        }
        item.setStatus(status);
        this.repository.update(item);
        if (status == 2) {
            this.revokeAllApiTokens(item);
        }
        return item;
    }

    public boolean exists(String email) {
        return this.findByEmail(email).isPresent();
    }

    public Optional<UserImpl> findByEmail(String email) {
        return this.data.values().stream().filter(it -> Objects.equals(it.getEmail(), email)).findFirst();
    }

    public List<ApiToken> getApiSessions(User user) {
        return this.apiTokenManager.get(user.getId());
    }

    public Optional<ApiToken> getApiSession(String token) {
        return this.apiTokenManager.checkApiKey(token);
    }

    public Pair<String, ApiToken> issueToken(User user, String name, Duration validFor) {
        return this.apiTokenManager.issue(user.getId(), name, validFor);
    }

    public void revokeToken(User user, int tokenId) {
        this.apiTokenManager.revoke(user, tokenId);
    }

    public void revokeAllApiTokens(User user) {
        this.apiTokenManager.revokeAll(user.getId());
    }

    public String generatePassword(String password) {
        String salt = new SIDGenerator().generateId(8).toLowerCase();
        String crypt = MD5.getMD5(MD5.getMD5(password) + salt);
        return crypt + ":" + salt;
    }

    public void setLanguage(UserImpl user, String language2) {
        UserImpl userImpl = this.data.get(user.getId());
        Optional.ofNullable(userImpl).map(UserImpl::getSettings).ifPresent(settings -> settings.setLang(language2));
        this.repository.update(user);
    }

    public static boolean isOriginalRoot(User user) {
        return user.getId() == 1;
    }

    @Override
    public User get(Integer id) {
        return this.getData().get(id);
    }

    @Override
    public List<UserImpl> getAll() {
        return this.getData().values().stream().toList();
    }

    @Override
    public User add(User user) {
        throw new UnsupportedOperationException("Cannot add user from plugin");
    }

    @Override
    public User update(User user) {
        throw new UnsupportedOperationException("Cannot update user from plugin");
    }

    @Override
    public User delete(Integer id) {
        throw new UnsupportedOperationException("Cannot delete user from plugin");
    }

    @Override
    public User delete(User user) {
        return this.delete(user.getId());
    }

    @Override
    public User getUser(Context ctx) throws UnauthorizedResponse {
        return this.authUserRetriever.retrieve(ctx, (List<UserImpl>)this.getAll());
    }

    @Override
    public UserImpl getUser(WsContext ctx) {
        return this.authUserRetriever.retrieve(ctx, (List<UserImpl>)this.getAll());
    }

    public Map<Integer, UserImpl> getData() {
        return this.data;
    }

    public UsersRepository getRepository() {
        return this.repository;
    }

    public RolesManager getRoleManager() {
        return this.roleManager;
    }

    public ApiTokenManager getApiTokenManager() {
        return this.apiTokenManager;
    }

    public SessionHandlerImpl getSessionHandler() {
        return this.sessionHandler;
    }

    public AuthUserRetriever getAuthUserRetriever() {
        return this.authUserRetriever;
    }

    public VezhaConfigManager getConfigManager() {
        return this.configManager;
    }

    public Provider<Auditor> getAuditor() {
        return this.auditor;
    }

    public Provider<VezhaLdapClient> getLdapClient() {
        return this.ldapClient;
    }

    @Inject
    public UsersManagerImpl(UsersRepository repository, RolesManager roleManager, ApiTokenManager apiTokenManager, SessionHandlerImpl sessionHandler, AuthUserRetriever authUserRetriever, VezhaConfigManager configManager, Provider<Auditor> auditor, Provider<VezhaLdapClient> ldapClient) {
        this.repository = repository;
        this.roleManager = roleManager;
        this.apiTokenManager = apiTokenManager;
        this.sessionHandler = sessionHandler;
        this.authUserRetriever = authUserRetriever;
        this.configManager = configManager;
        this.auditor = auditor;
        this.ldapClient = ldapClient;
    }
}

