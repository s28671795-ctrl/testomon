/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.h2.engine.ConnectionInfo;
import org.h2.jdbc.JdbcConnection;
import org.h2.util.IOUtils;
import org.h2.util.StringUtils;

public final class Upgrade {
    private static final String[] CHECKSUMS = new String[]{"6fca37906aa3916ba609f47258c4abb4c749cd51aa28718a2339d9aa234a480c", "3233d38ee11e15243f66c98ad388da9f12cf038a203cf507415081e3329ac4f4", "7451e9f234f32fd9f07e4e5e682c0595806a803de656228a43887a525019ea74", "5a4dfaf211d32860623fdc5627f12a9cf8446b9cfabc742e7c0bad26835a8bb1", "f75efcaf9ccb91d94de920322c32328435e9705c19cc06b510c5f09c0a6245bf", "0ca368055dd72d539084c916642147780c944b90d98d2306da86814b174d1145", "4d9143f5b80f8878ca56edc383ae6d0a183a3b5879e83228dbacbe288007455c", "3df7aedd564cf61a464f4e95ec364eb7bb2b51d36863ed54edeb6ff2fed7b376", "7e8af7b5eca6334013fc024dab02e173a017b2d1c22c8481ed64a6af873d0819", "9a705009830ae80a368b1b66c8ba63071845fe25d8f6b0964aa14a3f31b46bdd", "8810d72867508b033a68830024e7fe7dd5a99e6f5bbb38c5a933aeb23badff00", "c8debc05829db1db2e6b6507a3f0561e1f72bd966d36f322bdf294baca29ed22", "75819d4adbf76d66af904e76b52b57afe26e9bc0e15aceed4e3c72cd7586b0d3", "c9ea3e95e77ae560322bca37d51601ae4b1d07ae90988af1e9fe1ceda80cd9ce", "1f4753d8d862d7d22d234625f617d3d7e91b73799c89b8a6036895f944a863eb", "eed53fcd3cf6e1159c90e57ef2b4cbd1fa3aff7a936988bb018af6fc17a2b6d9", "d3101d540ed004493952732d28bdf90a7968990bab7a2e04d16805469aa4eedd", "035dd78af874ada48339b90e8e4f1ffba0f32bb0fa37dec37ed523afa96a9c32", "1d03156b22b40812e39cca4d250eededfed4db8476bfbae78d60a24975cbe6d8", "8102cc96257d71caeff04f02c97020ae39268a32c1f0aa8fcdfda4e948ce48c8", "134ceafcae6ca661d8acd64c8e67d30f6ead609065dba9f6d3a0cde0d7bef6e3", "e453faccaaf7d8fe4eb8be744549c4a2395c7b3dcfcbc19173588c3756baff1e", "5973b4b467f1e0a69cf8c7b02d03d9dcadb4171d8a9635c85442a5829200e76f", "711cc225d8fe5325458c3947dda2093ef3a1cd4923e916082b27e87e41ca6735", "682f6997495a8389f4881b93cb8224685b9c6cbed487bcb445712402e52a4b80", "1407913cc6ba2f8c2928e8ad544c232273365d6eb66fdf84ec4213abf71449d5", "7756a89f10d5d5df23936bbb613de8b481e32d1099e5228968046fee62fee882", "2649d19db9eebbddc826029d236886dfece9404cd108ca590e82d3fd7d888278", "66f9389748f176c11c66c201a3737ebad0b1f4ace37cc2cd3da8962c92c72128", "7c3e3b93ffaf617393126870be7f8e1708bbe8e05b931c51c638a8cb03f79a36", "1d6dc1095d3d4b105a99034ab61ab5943c4dbb31551e7b244b403cb3c324964f", "8eabfde7cf64cedb7c25dc25ee7fe75a633c5cbeb18a1060da2045293fd53b14", "a9840c6024f8570ad3aa4d54388b4dd605640cb5ab163c444a123f7d4739aa09", "33d80491417eb117a0d64442dc3e60b78cf014ad099bb36a55d3835bb69e6248", "f153d03466acc00b66e699213fe092277e457502b5caf48c417ed3745f50eaac", "244b29d22939b43ecdcd3b0bfd279899df18e3af20a50241278b5b27bcf1a902", "070f9e4898044880e01232b269fea5285dbf7b814b7092701e755aa7d6941832", "4666d8f01c661054b973bc0f01f8b20f298d8e134e6fd26d78c74d43eeffd54e", "b0d95f18474beea619fcfba83f033e5702483457e0f0a1d1ffb4b757c5182582", "17aa5ced25f13f9adc2820e0ccc3010e3ce55944d10c9e2c0c631b77674d039b", "7fe66e211202733c52f02a328b55b30975287d9c509751bf87507e6227c6a2a7", "42e2ebbb7bdf29dd2de4ab16fc8fb511af6337d223afd66a5ee5fe183de05d57", "89e362f9525adf36d58487ff756ee93254bf92595a7098258a4c030e08e0742e", "1d1be843af365e8881e22732c8640e2b04c2821a0d7aa61d4152ac3f991bb735", "dbc88bb8cd8177b5f13b655d6afb525637129369422f0b7be0fe187950ea5132", "03f60ca37c0124fd2b9b177726396a51853ed0cade444e1674a090b73d341b08", "35103656071f1ffd1078b1a8c8028c9577297f31c5f8c7dcc845c7b4b6392619", "fa97521a2e72174485a96276bcf6f573d5e44ca6aba2f62de87b33b5bb0d4b91", "46d7ff55ccd910def16f9afd21d983f2eb2f9a6850fb501916f6673caebc2694", "0d99d51b8d7b8e94732d048438b9f555e031ecd52225613d7bea45290571886d", "0aca5eea86e8619e91ad61b82b77fb9d0e51e939c5603ab8da41be32c6f25664", "144d4ddb5d9f610b8b26809f1c65f442864cc55136325d3f02d7a93fb878a1db", "6ca30e38ccaa0c6f4264ef013327ef9ba5303f4be3d8fdbce0c3ae6451178c1e", "43908ee9db698cb335e2b85375d68a9d03d818869a0542b85d8d4e416619795b", "990b94cdfc89987281af4168fc2f6c9067be96a8533f5a6eb0f33da4d30d3e4b", "cc329a8742fb6e7168b00ebd0015816ff0d2462409add7c9d223826486de4691", "6ae3cc11a8bbaa5bd1d8494e62bccea4d354eaf042da468eac3bc5009fd33b67", "f281673f3248a4b5cb03fdc0cc39b944fe978366be959d0e8106fcc3197f4705", "da08fef0b2bc0ff8876f895e17605daf514405a064e3c2c11d2275a19d301be6", "2b76304ce4256ee9fd61156f9b6ef82c049ffdc8dc89af07fcf59e9532c7e7cd", "16428fd1e6a3e5baa8067c1c2e777e1e99af68c6ef3ff7fbbf1938937a048a82", "44673ff2834428fdb7f11dac3b9d679fb3039ea32194a69452971fdd7150a08b", "1025d0d70a4e899c41bc8fd7370cd3768826e78da91b66fd9357e44d03d79d30", "b3ff2ebe161976124965a9a841877ec4f6e913dbadcc31af27f1b99f6abd57e9", "9e47e14d5b4b9ead127b15a33b107ff06f0a7dd3f98b5d6c149e6ccae05dc0a2", "c4ac74be5971445e270bbd4344be58d9a06dc927223614217e5a87257a7edc03", "e3b7a39a2b45b61fa1521ef33b3ba676a5a9e1a397bc3ef4fb678d861a1b0ae4", "6204d0c206443681911fb9e04a3af5198b253b5627d16d8d8d79180d13319212", "11d6bff477f7ca392288f5f6d42ee61d0ccb63a34c99ba2d91710b2409673897", "c8dac03b66c8011cca4e44dcc7a8b1c8f8df769927c7672be1704e76f9ee7926", "23ba495a07bbbb3bd6c3084d10a96dad7a23741b8b6d64b213459a784195a98c", "e21ea665b74ec0115344b5afda5ec70ea27b528c3f103524e74c9854b1c4a284", "225b22e9857235c46c93861410b60b8c81c10dc8985f4faf188985ba5445126c", "b1cf34c64871014aa73580281cc464dfa72450d8860cc0752fc175e87edd6544", "b5b0c1836cead6831a50bd3e1b6c16fe6e583d4d2b7c4f41b4f838745c27cd01", "b99ea1f785c62b2a021664e72de696f8ea896f0da392a1c7baa3d4d47020b126", "0a05f4a0d5b85840148aadce63a423b5d3c36ef44756389b4faad08d2733faf5", "37f5216e14af2772930dff9b8734353f0a80e89ba3f33e065441de6537c5e842", "32dd6b149cb722aa4c2dd4d40a74a9cd41e32ac59a4e755a66e5753660d61d46", "3125a16743bc6b4cfbb61abba783203f1fb68230aa0fdc97898f796f99a5d42e", "3ad9ac4b6aae9cd9d3ac1c447465e1ed06019b851b893dd6a8d76ddb6d85bca6", "95090f0609aacb0ee339128ef04077145ef28320ee874ea2e33a692938da5b97", "712a616409580bd4ac7c10e48f2599cc32ba3a433a1804da619c3f0a5ef66a04", "3b9607c5673fd8b87e49e3ac46bd88fd3561e863dce673a35234e8b5708f3deb", null, "edc57299926297fd9315e04de75f8538c4cb5fe97fd3da2a1e5cee6a4c98b5cd", "db9284c6ff9bf3bc0087851edbd34563f1180df3ae87c67c5fe2203c0e67a536", "d623cdc0f61d218cf549a8d09f1c391ff91096116b22e2475475fce4fbe72bd0", null, null};
    private static final String REPOSITORY = "https://repo1.maven.org/maven2";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean upgrade(String string, Properties properties, int n) throws Exception {
        Throwable throwable;
        Connection connection;
        ConnectionInfo connectionInfo;
        Properties properties2 = new Properties();
        properties2.putAll((Map<?, ?>)properties);
        Object object = properties.get("password");
        if (object instanceof char[]) {
            properties2.put("password", ((char[])object).clone());
        }
        if (!(connectionInfo = new ConnectionInfo(string, properties, null, null)).isPersistent() || connectionInfo.isRemote()) {
            return false;
        }
        String string2 = connectionInfo.getName();
        String string3 = string2 + ".script.sql";
        StringBuilder stringBuilder = new StringBuilder("jdbc:h2:").append(string2).append(";ACCESS_MODE_DATA=r");
        Upgrade.copyProperty(connectionInfo, stringBuilder, "FILE_LOCK");
        Upgrade.copyProperty(connectionInfo, stringBuilder, "MV_STORE");
        String string4 = Upgrade.copyProperty(connectionInfo, stringBuilder, "CIPHER");
        String string5 = string4 == null ? "" : " CIPHER AES PASSWORD '" + UUID.randomUUID() + "' --hide--";
        Driver driver = Upgrade.loadH2(n);
        try {
            connection = driver.connect(stringBuilder.toString(), properties2);
            throwable = null;
            try {
                connection.createStatement().execute(StringUtils.quoteStringSQL(new StringBuilder("SCRIPT TO "), string3).append(string5).toString());
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (connection != null) {
                    if (throwable != null) {
                        try {
                            connection.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        connection.close();
                    }
                }
            }
        }
        finally {
            Upgrade.unloadH2(driver);
        }
        Upgrade.rename(string2, false);
        try {
            connection = new JdbcConnection(string, properties, null, null, false);
            throwable = null;
            try {
                StringBuilder stringBuilder2 = StringUtils.quoteStringSQL(new StringBuilder("RUNSCRIPT FROM "), string3).append(string5);
                if (n <= 200) {
                    stringBuilder2.append(" FROM_1X");
                }
                ((JdbcConnection)connection).createStatement().execute(stringBuilder2.toString());
            }
            catch (Throwable throwable4) {
                throwable = throwable4;
                throw throwable4;
            }
            finally {
                if (connection != null) {
                    if (throwable != null) {
                        try {
                            ((JdbcConnection)connection).close();
                        }
                        catch (Throwable throwable5) {
                            throwable.addSuppressed(throwable5);
                        }
                    } else {
                        ((JdbcConnection)connection).close();
                    }
                }
            }
        }
        catch (Throwable throwable6) {
            Upgrade.rename(string2, true);
            throw throwable6;
        }
        finally {
            Files.deleteIfExists(Paths.get(string3, new String[0]));
        }
        return true;
    }

    private static void rename(String string, boolean bl) throws IOException {
        Upgrade.rename(string, ".mv.db", bl);
        Upgrade.rename(string, ".lobs.db", bl);
    }

    private static void rename(String string, String string2, boolean bl) throws IOException {
        Object object;
        String string3 = string + string2;
        String string4 = string3 + ".bak";
        if (bl) {
            object = string3;
            string3 = string4;
            string4 = object;
        }
        if (Files.exists((Path)(object = Paths.get(string3, new String[0])), new LinkOption[0])) {
            Files.move((Path)object, Paths.get(string4, new String[0]), StandardCopyOption.ATOMIC_MOVE);
        }
    }

    private static String copyProperty(ConnectionInfo connectionInfo, StringBuilder stringBuilder, String string) {
        try {
            String string2 = connectionInfo.getProperty(string, null);
            if (string2 != null) {
                stringBuilder.append(';').append(string).append('=').append(string2);
            }
            return string2;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Driver loadH2(int n) throws IOException, ReflectiveOperationException {
        Object object;
        String string;
        if (n >= 201) {
            if ((n & 1) != 0 || n > 224) {
                throw new IllegalArgumentException("version=" + n);
            }
            int n2 = n / 100;
            int n3 = n / 10 % 10;
            string = "" + n2 + '.' + n3 + '.';
        } else if (n >= 177) {
            string = "1.4.";
        } else if (n >= 146 && n != 147) {
            string = "1.3.";
        } else if (n >= 120) {
            string = "1.2.";
        } else {
            throw new IllegalArgumentException("version=" + n);
        }
        String string2 = string + n;
        byte[] byArray = Upgrade.downloadUsingMaven("com.h2database", "h2", string2, CHECKSUMS[n >= 202 ? (n >>> 1) - 20 : n - 120]);
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(byArray));
        final HashMap<String, byte[]> hashMap = new HashMap<String, byte[]>(n >= 198 ? 2048 : 1024);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while ((object = zipInputStream.getNextEntry()) != null) {
            if (((ZipEntry)object).isDirectory()) continue;
            IOUtils.copy(zipInputStream, (OutputStream)byteArrayOutputStream);
            hashMap.put(((ZipEntry)object).getName(), byteArrayOutputStream.toByteArray());
            byteArrayOutputStream.reset();
        }
        object = new ClassLoader(null){

            @Override
            protected Class<?> findClass(String string) throws ClassNotFoundException {
                String string2 = string.replace('.', '/') + ".class";
                byte[] byArray = (byte[])hashMap.get(string2);
                if (byArray == null) {
                    return ClassLoader.getSystemClassLoader().loadClass(string);
                }
                return this.defineClass(string, byArray, 0, byArray.length);
            }

            @Override
            public InputStream getResourceAsStream(String string) {
                byte[] byArray = (byte[])hashMap.get(string);
                return byArray != null ? new ByteArrayInputStream(byArray) : null;
            }
        };
        return (Driver)((ClassLoader)object).loadClass("org.h2.Driver").getDeclaredMethod("load", new Class[0]).invoke(null, new Object[0]);
    }

    public static void unloadH2(Driver driver) throws ReflectiveOperationException {
        driver.getClass().getDeclaredMethod("unload", new Class[0]).invoke(null, new Object[0]);
    }

    private static byte[] downloadUsingMaven(String string, String string2, String string3, String string4) throws IOException {
        String string5 = string.replace('.', '/') + '/' + string2 + '/' + string3 + '/' + string2 + '-' + string3 + ".jar";
        Path path = Paths.get(System.getProperty("user.home") + "/.m2/repository", new String[0]);
        if (Files.isDirectory(path, new LinkOption[0])) {
            Path path2 = path.resolve(string5);
            if (!Files.exists(path2, new LinkOption[0])) {
                try {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                        arrayList.add("cmd");
                        arrayList.add("/C");
                    }
                    arrayList.add("mvn");
                    arrayList.add("org.apache.maven.plugins:maven-dependency-plugin:2.1:get");
                    arrayList.add("-DrepoUrl=https://repo1.maven.org/maven2");
                    arrayList.add("-Dartifact=" + string + ':' + string2 + ':' + string3);
                    Upgrade.exec(arrayList);
                }
                catch (RuntimeException runtimeException) {
                    System.out.println("Could not download using Maven: " + runtimeException.toString());
                }
            }
            if (Files.exists(path2, new LinkOption[0])) {
                return Upgrade.check(Files.readAllBytes(path2), string4, path2.toAbsolutePath().toString());
            }
        }
        return Upgrade.download("https://repo1.maven.org/maven2/" + string5, string4);
    }

    private static int exec(ArrayList<String> arrayList) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
            processBuilder.command(arrayList.toArray(new String[0]));
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            process.waitFor();
            return process.exitValue();
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static byte[] download(String string, String string2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            System.out.println("Downloading " + string);
            URL uRL = new URL(string);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(uRL.openStream());
            long l = System.nanoTime();
            int n = 0;
            while (true) {
                long l2;
                if ((l2 = System.nanoTime()) - l > 1000000000L) {
                    System.out.println("Downloaded " + n + " bytes");
                    l = l2;
                }
                int n2 = ((InputStream)bufferedInputStream).read();
                ++n;
                if (n2 < 0) break;
                byteArrayOutputStream.write(n2);
            }
            ((InputStream)bufferedInputStream).close();
        }
        catch (IOException iOException) {
            throw new RuntimeException("Error downloading " + string, iOException);
        }
        return Upgrade.check(byteArrayOutputStream.toByteArray(), string2, null);
    }

    private static byte[] check(byte[] byArray, String string, String string2) {
        String string3 = Upgrade.getSHA256(byArray);
        if (string == null) {
            System.out.println('\"' + string3 + '\"');
        } else if (!string3.equals(string)) {
            StringBuilder stringBuilder = new StringBuilder().append("SHA-256 checksum mismatch; got: ").append(string3).append(" expected: ").append(string);
            if (string2 != null) {
                stringBuilder.append(" for file ").append(string2);
            }
            throw new RuntimeException(stringBuilder.toString());
        }
        return byArray;
    }

    private static String getSHA256(byte[] byArray) {
        try {
            return StringUtils.convertBytesToHex(MessageDigest.getInstance("SHA-256").digest(byArray));
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }

    private Upgrade() {
    }
}

