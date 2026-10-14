/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.license4j.License;
import com.license4j.validator.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;

public class LicenseDataDTO {
    private String ls;
    private Long lsid;
    private Long lsaid;
    private int pa;
    private String action;
    private String cchid;
    private String rv;
    private String chids;
    private String ufn;
    private String urt;
    private String uem;
    private String uco;
    private String ust;
    private String ute;
    private String uct;
    private String uzi;
    private String ucu;
    private int mod;
    private String chon;
    private String os;
    @JSONField(serialize=false)
    private String url;
    @JSONField(serialize=false)
    private boolean deactivate;

    public LicenseDataDTO(String publicKey, String hostName, String hardwareResult, License license, String url2, boolean deactivate) {
        this.action = deactivate ? "deactivate" : "activate";
        this.ls = license.getLicenseString();
        this.chon = hostName;
        this.chids = hardwareResult;
        this.rv = "2.0.5";
        this.os = ValidatorUtils.getOSName();
        this.pa = ValidatorUtils.getPublicKeyHashcode(publicKey);
        this.lsid = license.getLicenseText() != null ? Long.valueOf(license.getLicenseText().getLicenseID()) : null;
        this.lsaid = deactivate && license.getLicenseText() != null ? Long.valueOf(license.getLicenseText().getActivationID()) : null;
        this.mod = 0;
        this.url = url2;
        this.deactivate = deactivate;
    }

    public LicenseDataDTO(String paramsStr) {
        String[] params = StringUtils.split(paramsStr, '@');
        if (params.length < 4) {
            return;
        }
        this.setAction("activate");
        this.setChon(params[0]);
        this.setOs(params[1]);
        this.setCchid(params[2]);
        this.setChids(params[2]);
        this.setLs(params[3]);
        if (params.length == 5) {
            this.setChon(params[0].replaceAll("[^\\u0000-\\uFFFF]", ""));
            this.setOs(params[1].replaceAll("[^\\u0000-\\uFFFF]", ""));
            this.setPa(Integer.parseInt(params[3]));
            this.setLs(params[4]);
        } else if (params.length > 5) {
            this.setPa(Integer.parseInt(params[3]));
            this.setLs(params[4]);
            this.setMod(params[5].equalsIgnoreCase("true") ? 1 : 0);
            if (!params[6].equals("~")) {
                this.setUfn(params[6]);
            }
            if (!params[7].equals("~")) {
                this.setUrt(params[7]);
            }
            if (!params[8].equals("~")) {
                this.setUem(params[8]);
            }
            if (!params[9].equals("~")) {
                this.setUco(params[9]);
            }
            if (!params[10].equals("~")) {
                this.setUst(params[10]);
            }
            if (!params[11].equals("~")) {
                this.setUte(params[11]);
            }
            if (!params[13].equals("~")) {
                this.setUct(params[13]);
            }
            if (!params[14].equals("~")) {
                this.setUzi(params[14]);
            }
            if (!params[15].equals("~")) {
                this.setUcu(params[15]);
            }
        }
    }

    public static LicenseDataDTOBuilder builder() {
        return new LicenseDataDTOBuilder();
    }

    public String getLs() {
        return this.ls;
    }

    public Long getLsid() {
        return this.lsid;
    }

    public Long getLsaid() {
        return this.lsaid;
    }

    public int getPa() {
        return this.pa;
    }

    public String getAction() {
        return this.action;
    }

    public String getCchid() {
        return this.cchid;
    }

    public String getRv() {
        return this.rv;
    }

    public String getChids() {
        return this.chids;
    }

    public String getUfn() {
        return this.ufn;
    }

    public String getUrt() {
        return this.urt;
    }

    public String getUem() {
        return this.uem;
    }

    public String getUco() {
        return this.uco;
    }

    public String getUst() {
        return this.ust;
    }

    public String getUte() {
        return this.ute;
    }

    public String getUct() {
        return this.uct;
    }

    public String getUzi() {
        return this.uzi;
    }

    public String getUcu() {
        return this.ucu;
    }

    public int getMod() {
        return this.mod;
    }

    public String getChon() {
        return this.chon;
    }

    public String getOs() {
        return this.os;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean isDeactivate() {
        return this.deactivate;
    }

    public void setLs(String ls) {
        this.ls = ls;
    }

    public void setLsid(Long lsid) {
        this.lsid = lsid;
    }

    public void setLsaid(Long lsaid) {
        this.lsaid = lsaid;
    }

    public void setPa(int pa) {
        this.pa = pa;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setCchid(String cchid) {
        this.cchid = cchid;
    }

    public void setRv(String rv) {
        this.rv = rv;
    }

    public void setChids(String chids) {
        this.chids = chids;
    }

    public void setUfn(String ufn) {
        this.ufn = ufn;
    }

    public void setUrt(String urt) {
        this.urt = urt;
    }

    public void setUem(String uem) {
        this.uem = uem;
    }

    public void setUco(String uco) {
        this.uco = uco;
    }

    public void setUst(String ust) {
        this.ust = ust;
    }

    public void setUte(String ute) {
        this.ute = ute;
    }

    public void setUct(String uct) {
        this.uct = uct;
    }

    public void setUzi(String uzi) {
        this.uzi = uzi;
    }

    public void setUcu(String ucu) {
        this.ucu = ucu;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }

    public void setChon(String chon) {
        this.chon = chon;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public void setDeactivate(boolean deactivate) {
        this.deactivate = deactivate;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LicenseDataDTO)) {
            return false;
        }
        LicenseDataDTO other = (LicenseDataDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getPa() != other.getPa()) {
            return false;
        }
        if (this.getMod() != other.getMod()) {
            return false;
        }
        if (this.isDeactivate() != other.isDeactivate()) {
            return false;
        }
        Long this$lsid = this.getLsid();
        Long other$lsid = other.getLsid();
        if (this$lsid == null ? other$lsid != null : !((Object)this$lsid).equals(other$lsid)) {
            return false;
        }
        Long this$lsaid = this.getLsaid();
        Long other$lsaid = other.getLsaid();
        if (this$lsaid == null ? other$lsaid != null : !((Object)this$lsaid).equals(other$lsaid)) {
            return false;
        }
        String this$ls = this.getLs();
        String other$ls = other.getLs();
        if (this$ls == null ? other$ls != null : !this$ls.equals(other$ls)) {
            return false;
        }
        String this$action = this.getAction();
        String other$action = other.getAction();
        if (this$action == null ? other$action != null : !this$action.equals(other$action)) {
            return false;
        }
        String this$cchid = this.getCchid();
        String other$cchid = other.getCchid();
        if (this$cchid == null ? other$cchid != null : !this$cchid.equals(other$cchid)) {
            return false;
        }
        String this$rv = this.getRv();
        String other$rv = other.getRv();
        if (this$rv == null ? other$rv != null : !this$rv.equals(other$rv)) {
            return false;
        }
        String this$chids = this.getChids();
        String other$chids = other.getChids();
        if (this$chids == null ? other$chids != null : !this$chids.equals(other$chids)) {
            return false;
        }
        String this$ufn = this.getUfn();
        String other$ufn = other.getUfn();
        if (this$ufn == null ? other$ufn != null : !this$ufn.equals(other$ufn)) {
            return false;
        }
        String this$urt = this.getUrt();
        String other$urt = other.getUrt();
        if (this$urt == null ? other$urt != null : !this$urt.equals(other$urt)) {
            return false;
        }
        String this$uem = this.getUem();
        String other$uem = other.getUem();
        if (this$uem == null ? other$uem != null : !this$uem.equals(other$uem)) {
            return false;
        }
        String this$uco = this.getUco();
        String other$uco = other.getUco();
        if (this$uco == null ? other$uco != null : !this$uco.equals(other$uco)) {
            return false;
        }
        String this$ust = this.getUst();
        String other$ust = other.getUst();
        if (this$ust == null ? other$ust != null : !this$ust.equals(other$ust)) {
            return false;
        }
        String this$ute = this.getUte();
        String other$ute = other.getUte();
        if (this$ute == null ? other$ute != null : !this$ute.equals(other$ute)) {
            return false;
        }
        String this$uct = this.getUct();
        String other$uct = other.getUct();
        if (this$uct == null ? other$uct != null : !this$uct.equals(other$uct)) {
            return false;
        }
        String this$uzi = this.getUzi();
        String other$uzi = other.getUzi();
        if (this$uzi == null ? other$uzi != null : !this$uzi.equals(other$uzi)) {
            return false;
        }
        String this$ucu = this.getUcu();
        String other$ucu = other.getUcu();
        if (this$ucu == null ? other$ucu != null : !this$ucu.equals(other$ucu)) {
            return false;
        }
        String this$chon = this.getChon();
        String other$chon = other.getChon();
        if (this$chon == null ? other$chon != null : !this$chon.equals(other$chon)) {
            return false;
        }
        String this$os = this.getOs();
        String other$os = other.getOs();
        if (this$os == null ? other$os != null : !this$os.equals(other$os)) {
            return false;
        }
        String this$url = this.getUrl();
        String other$url = other.getUrl();
        return !(this$url == null ? other$url != null : !this$url.equals(other$url));
    }

    protected boolean canEqual(Object other) {
        return other instanceof LicenseDataDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getPa();
        result = result * 59 + this.getMod();
        result = result * 59 + (this.isDeactivate() ? 79 : 97);
        Long $lsid = this.getLsid();
        result = result * 59 + ($lsid == null ? 43 : ((Object)$lsid).hashCode());
        Long $lsaid = this.getLsaid();
        result = result * 59 + ($lsaid == null ? 43 : ((Object)$lsaid).hashCode());
        String $ls = this.getLs();
        result = result * 59 + ($ls == null ? 43 : $ls.hashCode());
        String $action = this.getAction();
        result = result * 59 + ($action == null ? 43 : $action.hashCode());
        String $cchid = this.getCchid();
        result = result * 59 + ($cchid == null ? 43 : $cchid.hashCode());
        String $rv = this.getRv();
        result = result * 59 + ($rv == null ? 43 : $rv.hashCode());
        String $chids = this.getChids();
        result = result * 59 + ($chids == null ? 43 : $chids.hashCode());
        String $ufn = this.getUfn();
        result = result * 59 + ($ufn == null ? 43 : $ufn.hashCode());
        String $urt = this.getUrt();
        result = result * 59 + ($urt == null ? 43 : $urt.hashCode());
        String $uem = this.getUem();
        result = result * 59 + ($uem == null ? 43 : $uem.hashCode());
        String $uco = this.getUco();
        result = result * 59 + ($uco == null ? 43 : $uco.hashCode());
        String $ust = this.getUst();
        result = result * 59 + ($ust == null ? 43 : $ust.hashCode());
        String $ute = this.getUte();
        result = result * 59 + ($ute == null ? 43 : $ute.hashCode());
        String $uct = this.getUct();
        result = result * 59 + ($uct == null ? 43 : $uct.hashCode());
        String $uzi = this.getUzi();
        result = result * 59 + ($uzi == null ? 43 : $uzi.hashCode());
        String $ucu = this.getUcu();
        result = result * 59 + ($ucu == null ? 43 : $ucu.hashCode());
        String $chon = this.getChon();
        result = result * 59 + ($chon == null ? 43 : $chon.hashCode());
        String $os = this.getOs();
        result = result * 59 + ($os == null ? 43 : $os.hashCode());
        String $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        return result;
    }

    public String toString() {
        return "LicenseDataDTO(ls=" + this.getLs() + ", lsid=" + this.getLsid() + ", lsaid=" + this.getLsaid() + ", pa=" + this.getPa() + ", action=" + this.getAction() + ", cchid=" + this.getCchid() + ", rv=" + this.getRv() + ", chids=" + this.getChids() + ", ufn=" + this.getUfn() + ", urt=" + this.getUrt() + ", uem=" + this.getUem() + ", uco=" + this.getUco() + ", ust=" + this.getUst() + ", ute=" + this.getUte() + ", uct=" + this.getUct() + ", uzi=" + this.getUzi() + ", ucu=" + this.getUcu() + ", mod=" + this.getMod() + ", chon=" + this.getChon() + ", os=" + this.getOs() + ", url=" + this.getUrl() + ", deactivate=" + this.isDeactivate() + ")";
    }

    public LicenseDataDTO() {
    }

    public LicenseDataDTO(String ls, Long lsid, Long lsaid, int pa, String action, String cchid, String rv, String chids, String ufn, String urt, String uem, String uco, String ust, String ute, String uct, String uzi, String ucu, int mod, String chon, String os, String url2, boolean deactivate) {
        this.ls = ls;
        this.lsid = lsid;
        this.lsaid = lsaid;
        this.pa = pa;
        this.action = action;
        this.cchid = cchid;
        this.rv = rv;
        this.chids = chids;
        this.ufn = ufn;
        this.urt = urt;
        this.uem = uem;
        this.uco = uco;
        this.ust = ust;
        this.ute = ute;
        this.uct = uct;
        this.uzi = uzi;
        this.ucu = ucu;
        this.mod = mod;
        this.chon = chon;
        this.os = os;
        this.url = url2;
        this.deactivate = deactivate;
    }

    public static class LicenseDataDTOBuilder {
        private String ls;
        private Long lsid;
        private Long lsaid;
        private int pa;
        private String action;
        private String cchid;
        private String rv;
        private String chids;
        private String ufn;
        private String urt;
        private String uem;
        private String uco;
        private String ust;
        private String ute;
        private String uct;
        private String uzi;
        private String ucu;
        private int mod;
        private String chon;
        private String os;
        private String url;
        private boolean deactivate;

        LicenseDataDTOBuilder() {
        }

        public LicenseDataDTOBuilder ls(String ls) {
            this.ls = ls;
            return this;
        }

        public LicenseDataDTOBuilder lsid(Long lsid) {
            this.lsid = lsid;
            return this;
        }

        public LicenseDataDTOBuilder lsaid(Long lsaid) {
            this.lsaid = lsaid;
            return this;
        }

        public LicenseDataDTOBuilder pa(int pa) {
            this.pa = pa;
            return this;
        }

        public LicenseDataDTOBuilder action(String action) {
            this.action = action;
            return this;
        }

        public LicenseDataDTOBuilder cchid(String cchid) {
            this.cchid = cchid;
            return this;
        }

        public LicenseDataDTOBuilder rv(String rv) {
            this.rv = rv;
            return this;
        }

        public LicenseDataDTOBuilder chids(String chids) {
            this.chids = chids;
            return this;
        }

        public LicenseDataDTOBuilder ufn(String ufn) {
            this.ufn = ufn;
            return this;
        }

        public LicenseDataDTOBuilder urt(String urt) {
            this.urt = urt;
            return this;
        }

        public LicenseDataDTOBuilder uem(String uem) {
            this.uem = uem;
            return this;
        }

        public LicenseDataDTOBuilder uco(String uco) {
            this.uco = uco;
            return this;
        }

        public LicenseDataDTOBuilder ust(String ust) {
            this.ust = ust;
            return this;
        }

        public LicenseDataDTOBuilder ute(String ute) {
            this.ute = ute;
            return this;
        }

        public LicenseDataDTOBuilder uct(String uct) {
            this.uct = uct;
            return this;
        }

        public LicenseDataDTOBuilder uzi(String uzi) {
            this.uzi = uzi;
            return this;
        }

        public LicenseDataDTOBuilder ucu(String ucu) {
            this.ucu = ucu;
            return this;
        }

        public LicenseDataDTOBuilder mod(int mod) {
            this.mod = mod;
            return this;
        }

        public LicenseDataDTOBuilder chon(String chon) {
            this.chon = chon;
            return this;
        }

        public LicenseDataDTOBuilder os(String os) {
            this.os = os;
            return this;
        }

        public LicenseDataDTOBuilder url(String url2) {
            this.url = url2;
            return this;
        }

        public LicenseDataDTOBuilder deactivate(boolean deactivate) {
            this.deactivate = deactivate;
            return this;
        }

        public LicenseDataDTO build() {
            return new LicenseDataDTO(this.ls, this.lsid, this.lsaid, this.pa, this.action, this.cchid, this.rv, this.chids, this.ufn, this.urt, this.uem, this.uco, this.ust, this.ute, this.uct, this.uzi, this.ucu, this.mod, this.chon, this.os, this.url, this.deactivate);
        }

        public String toString() {
            return "LicenseDataDTO.LicenseDataDTOBuilder(ls=" + this.ls + ", lsid=" + this.lsid + ", lsaid=" + this.lsaid + ", pa=" + this.pa + ", action=" + this.action + ", cchid=" + this.cchid + ", rv=" + this.rv + ", chids=" + this.chids + ", ufn=" + this.ufn + ", urt=" + this.urt + ", uem=" + this.uem + ", uco=" + this.uco + ", ust=" + this.ust + ", ute=" + this.ute + ", uct=" + this.uct + ", uzi=" + this.uzi + ", ucu=" + this.ucu + ", mod=" + this.mod + ", chon=" + this.chon + ", os=" + this.os + ", url=" + this.url + ", deactivate=" + this.deactivate + ")";
        }
    }
}

