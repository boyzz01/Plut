import com.ardeveloper.plut.data.db.*
import com.ardeveloper.plut.data.response.ResponseCart
import com.ardeveloper.plut.data.response.ResponseShop
import com.ardeveloper.plut.data.response.ResponseProduk
import com.ardeveloper.plut.data.response.ResponseTransaksi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

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

//    @FormUrlEncoded
//    @POST("add_produk")
//    fun addProduk(
//        @Field("nama") nama: String,
//        @Field("harga") harga: Int,
//        @Field("kategori") kat: String,
//        @Field("umkm") umkm: String,
//        @Field("kota") kota: String,
//        @Field("stock") kode:Int, ): Call<ResponseBody>

    @Multipart
    @POST("add_produk")
    fun addProduk(
        @Part("nama") nama: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("kategori") kat: RequestBody,
        @Part("umkm") umkm: RequestBody,
        @Part("kota") kota: RequestBody,
        @Part foto:MultipartBody.Part,
        @Part("stock") stock:RequestBody ): Call<ResponseBody>

    @Multipart
    @POST("edit_produk")
    fun editProduk(
        @Part("kode") kode: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part foto:MultipartBody.Part,
        @Part("stock") stock:RequestBody ): Call<ResponseBody>


    @Multipart
    @POST("edit_produk")
    fun editProduk(
        @Part("kode") kode: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("stock") stock:RequestBody ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("add_umkm")
    fun addUmkm(
        @Field("nama") nama: String,
        @Field("nib") nib: Int,
        @Field("kode_kota") kode:String): Call<ResponseBody>


    @FormUrlEncoded
    @POST("retur")
    fun returProduk(
        @Field("kode") kode: String,
        @Field("jumlah") jumlah: Int,
        @Field("keterangan") ket: String,
        @Field("user") user: String,): Call<ResponseBody>


    @FormUrlEncoded
    @POST("add_cart")
    fun addCart(
        @Field("product_id") p_id: String,
        @Field("user_id") user_id: Int,
        @Field("jumlah") jumlah: Int): Call<ResponseBody>


    @FormUrlEncoded
    @POST("add_transaksi")
    fun addTransaksi(
        @Field("total_uang") uang: Int,
        @Field("user_id") user_id: Int,
        @Field("diskon") diskon: Int,
        @Field("total") total: Int,
        @Field("kembalian") kembalian: Int,
        @Field("subtotal") subtotal: Int,
        @Field("uang_diterima") bayar: Int): Call<ResponseBody>

    @Multipart
    @POST("add_umkm")
    fun addUmkmWithFoto(
        @Part("nama") nama: RequestBody,
        @Part("nib") nib: RequestBody,
        @Part foto:MultipartBody.Part,
        @Part("kode_kota") kode:RequestBody): Call<ResponseBody>


    @GET("produk_umkm/{kode}")
    suspend fun getProduk(
        @Path("kode") kode: String
    ): List<Product>

    @GET("get_cart/{kode}")
    fun getCart(
        @Path("kode") kode: String
    ): Call<ResponseCart>


    @GET("get_transaksi/{kode}")
    fun getTransksi(
        @Path("kode") kode: String
    ): Call<ResponseTransaksi>

    @FormUrlEncoded
    @POST("produk_shop")
    fun getProdukShop(
        @Field("id_user") id_user: String
    ): Call<ResponseShop>

    @GET("produk_umkm/{kode}")
    fun getProdukbyId(
        @Path("kode") kode: String
    ): Call<List<Product>>

    @GET("produk")
    suspend fun getProduk(): List<Product>

    @GET("detail_produk/{kode}")
    suspend fun getProdukDetail(
        @Path("kode") kode: String
    ): Product

    @GET("detail_umkm/{kode}")
    suspend fun getUmkmDetail(
        @Path("kode") kode: String
    ): UMKM

}