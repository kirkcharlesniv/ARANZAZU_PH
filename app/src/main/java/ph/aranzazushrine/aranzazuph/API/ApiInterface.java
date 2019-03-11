package ph.aranzazushrine.aranzazuph.API;

import java.util.List;

import ph.aranzazushrine.aranzazuph.Models.News;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("post-minimal")
    Call<List<News>> getNews();
}
