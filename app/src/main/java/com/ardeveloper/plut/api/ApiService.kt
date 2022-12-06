import com.ardeveloper.plut.data.db.*
import com.ardeveloper.plut.data.response.*
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

    @GET("produk")
    fun getListProduk():Call<List<Product>>

    @GET("fast")
    fun fastReport():Call<List<Product>>

    @GET("slow")
    fun slowReport():Call<List<Product>>

    @GET("kota")
    suspend fun getKota(): List<Kota>

    @GET("umkm")
    suspend fun getUmkm(): List<UMKM>

    @GET("get_laporan")
    suspend fun getLaporan(): List<LaporanUmkm>

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
        @Field("uang_diterima") bayar: Int,
        @Field("metode") metode:String,
        @Field("bank")bank:String,
        @Field("nokartu")nokartu:String): Call<ResponseBody>

    @Multipart
    @POST("add_umkm")
    fun tambahUmkm(
        @Part("np") np : RequestBody,
        @Part("ap") ap : RequestBody,
        @Part("ttl") ttl: RequestBody,
        @Part("jk") jk: RequestBody,
        @Part("hp") hp: RequestBody,
        @Part("ktp") ktp: RequestBody,
        @Part("nu") nu: RequestBody,
        @Part("au") au: RequestBody,
        @Part("jp") jp: RequestBody,
        @Part("dp") dp: RequestBody,
        @Part("nib") nib: RequestBody,
        @Part("halal") halal: RequestBody,
        @Part("bpom") bpom: RequestBody,
        @Part("pirt") pirt: RequestBody,
        @Part("merk") merk: RequestBody,
        @Part("hak") hak: RequestBody,
        @Part("email") email: RequestBody,
        @Part("fb") fb: RequestBody,
        @Part("ig") ig: RequestBody,
        @Part("web") web: RequestBody,
        @Part("shopee") shopee: RequestBody,
        @Part("tokopedia") tokopedia: RequestBody,
        @Part("lain") lain: RequestBody,
        @Part foto:MultipartBody.Part,
        @Part("kode_kota") kode:RequestBody,
        @Part("nilai_asset") nilai_asset:RequestBody,
        @Part("omset") omset:RequestBody,
        @Part("karyawan") karyawan:RequestBody,
        @Part("tiktok") tiktok:RequestBody,
        @Part("youtube") youtube:RequestBody,
        @Part("sosmedlain") sosmedlain:RequestBody,
        @Part("lpse") lpse:RequestBody,
        @Part("mbiz") mbiz:RequestBody): Call<ResponseBody>

    @Multipart
    @POST("add_umkm")
    fun tambahUmkm(
        @Part("np") np : RequestBody,
        @Part("ap") ap : RequestBody,
        @Part("ttl") ttl: RequestBody,
        @Part("jk") jk: RequestBody,
        @Part("hp") hp: RequestBody,
        @Part("ktp") ktp: RequestBody,
        @Part("nu") nu: RequestBody,
        @Part("au") au: RequestBody,
        @Part("jp") jp: RequestBody,
        @Part("dp") dp: RequestBody,
        @Part("nib") nib: RequestBody,
        @Part("halal") halal: RequestBody,
        @Part("bpom") bpom: RequestBody,
        @Part("pirt") pirt: RequestBody,
        @Part("merk") merk: RequestBody,
        @Part("hak") hak: RequestBody,
        @Part("email") email: RequestBody,
        @Part("fb") fb: RequestBody,
        @Part("ig") ig: RequestBody,
        @Part("web") web: RequestBody,
        @Part("shopee") shopee: RequestBody,
        @Part("tokopedia") tokopedia: RequestBody,
        @Part("lain") lain: RequestBody,
        @Part("kode_kota") kode:RequestBody,
        @Part("nilai_asset") nilai_asset:RequestBody,
        @Part("omset") omset:RequestBody,
        @Part("karyawan") karyawan:RequestBody,
        @Part("tiktok") tiktok:RequestBody,
        @Part("youtube") youtube:RequestBody,
        @Part("sosmedlain") sosmedlain:RequestBody,
        @Part("lpse") lpse:RequestBody,
        @Part("mbiz") mbiz:RequestBody): Call<ResponseBody>

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

    @GET("detail_laporan/{kode}")
    fun getLaporanDetail(
        @Path("kode") kode: String
    ): Call<List<TransaksiItem>>

    @FormUrlEncoded
    @POST("produk_shop")
    fun getProdukShop(
        @Field("id_user") id_user: String
    ): Call<ResponseShop>

    @FormUrlEncoded
    @POST("hapus_umkm")
    fun hapusUmkm(
        @Field("kode") kode: String
    ): Call<ResponseBody>

    @GET("produk_umkm/{kode}")
    fun getProdukbyId(
        @Path("kode") kode: String
    ): Call<List<Product>>

    @GET("produk")
    suspend fun getProduk(): List<Product>



    @GET("history")
    suspend fun getHistory(): List<Transaksi>

    @GET("transaksi_user/{kode}")
    fun getTransaksiUser(
        @Path("kode") kode: String
    ):  Call<List<Transaksi>>

    @GET("detail_produk/{kode}")
    suspend fun getProdukDetail(
        @Path("kode") kode: String
    ): Product

    @GET("detail_umkm/{kode}")
    suspend fun getUmkmDetail(
        @Path("kode") kode: String
    ): UMKM

}