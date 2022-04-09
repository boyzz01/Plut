import com.ardeveloper.plut.data.db.*
import com.ardeveloper.plut.data.response.ResponseData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


//    @GET("index.php?r=json/trigercontrol")
//    suspend fun getTC(): ResponseToken

    @FormUrlEncoded
    @POST("login")
     fun checkUser(
        @Field("username") username: String,
        @Field("password") password:String): Call<ResponseBody>


    @GET("kota")
    suspend fun getKota(): List<Kota>

    @GET("umkm")
    suspend fun getUmkm(): List<UMKM>

    @GET("kategori")
    suspend fun getKategori(): List<Kategori>

    @FormUrlEncoded
    @POST("add_produk")
    fun addProduk(
        @Field("nama") nama: String,
        @Field("harga") harga: Int,
        @Field("kategori") kat: String,
        @Field("umkm") umkm: String,
        @Field("kota") kota: String,
        @Field("stock") kode:Int, ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("add_umkm")
    suspend fun addUmkm(
        @Field("nama") nama: String,
        @Field("nib") nib: Int,
        @Field("kode_kota") kode:String): ResponseData

    @GET("produk")
    suspend fun getProduk(): List<Product>

}