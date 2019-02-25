package ph.aranzazushrine.aranzazuph.API;

import ph.aranzazushrine.aranzazuph.News.Information;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("posts")
    Call<Information> getNews();
}
