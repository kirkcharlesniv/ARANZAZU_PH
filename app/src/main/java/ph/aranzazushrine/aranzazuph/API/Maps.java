package ph.aranzazushrine.aranzazuph.API;

public class Maps {
    private String mURL, mTitle, mDesc;

    public Maps(String url, String title, String desc) {
        mURL = url;
        mTitle = title;
        mDesc = desc;
    }

    public String getmURL() {
        return mURL;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }
}
