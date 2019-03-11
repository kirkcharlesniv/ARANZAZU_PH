package ph.aranzazushrine.aranzazuph.Author;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Avatar {
    @SerializedName("24")
    @Expose
    private String _24;
    @SerializedName("48")
    @Expose
    private String _48;
    @SerializedName("96")
    @Expose
    private String _96;

    public String get_24() {
        return _24;
    }

    public void set_24(String _24) {
        this._24 = _24;
    }

    public String get_48() {
        return _48;
    }

    public void set_48(String _48) {
        this._48 = _48;
    }

    public String get_96() {
        return _96;
    }

    public void set_96(String _96) {
        this._96 = _96;
    }
}
