package com.example.vendor.nextapp.http

import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.*
import retrofit2.http.POST




/**
 * Created by ly on 2020/3/23 10:25
 *
 */

interface RequestService {

    /**
     * ---------------------->>>>>>>>>>>>>>>> GET 篇 start<<<<<<<<<<<<<<<<<<<<<<-------------------------
     *
     * @url 动态添加url
     * @QueryMap 参数传 map 集合
     * @Query 单个参数传递
     *
     * @path 路径动态变
     * @body 请求体进行get
     *
     *
     * 为了下次不再百度 写详细点
     *
     * <REQEUST>  1,2,3 基本满足所有的GET请求
     * 后面是扩展，其实没啥卵用
     *
     * ----遗留 关于@Header的使用  ->>>作为参数和方法抬头
     *
     * ---------------------->>>>>>>>>>>>>>>> GET 篇 end<<<<<<<<<<<<<<<<<<<<<<-------------------------
     */

    /**
     * <REQUEST 1>
     *
     * 动态的请求方式 [url 动态，查询参数map动态]
     *
     * @param url { 动态添加url } 这个后面终止连接后续的参数，此后+“?”
     * @param map 参数数据结构"abc"="xxx"     [---&---]
     * @return 返回 ResponseBody的实体
     *
     * 效果图
     * http://xxjf.cdjg.chengdu.gov.cn:8090/jyptdbctl/video/getVideoPage?type=1&curPage=1&pageSize=4
     *  |-------------IP--------------|PORT|-----------URL--------------|---------MAP--------------|
     *
     *  这里的@param url  ----> URL
     *
     *  这里的@param map  ----> MAP   map["type"] = "1" ;map["curPage"] = "1";map["pageSize"] = "4"
     *
     *  返回的ResponseBody 可以写对应的解析实体类，也可以在解析出来的时候再转为对应的实体类
     */
    @GET
    fun getServer(@Url url: String, @QueryMap map: Map<String, String>): Call<ResponseBody>

    /**
     * <REQUEST 2>
     *
     * 静态模式的get请求 [url 固定]
     *
     * 效果图
     * http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello%20world
     * |------BASE_URL----|--------------URL--------------------------|
     *
     * 不带参数类型 直接GET请求,性质比较单一
     *
     * 返回的ResponseBody 可以写对应的解析实体类，也可以在解析出来的时候再转为对应的实体类
     */
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    fun getCall():Call<ResponseBody>

    /**
     * <REQUEST 3>
     *
     * 动态的请求方式 [url 静态，查询参数动态]
     *
     * @param type 类型
     * @param curPage 页数
     * @param pageSize 页量
     * @return 返回 ResponseBody的实体
     *
     * GET的实质是什么 其实是查询
     * @Query 用法，括号里面是url拼接的参数Key，变量是对key进行赋值
     *
     * 效果图
     * http://xxjf.cdjg.chengdu.gov.cn:8090/jyptdbctl/video/getVideoPage?type=1&curPage=1&pageSize=4
     * |-------------IP--------------|PORT|-----------URL------------ --|---------[K->V]-----------|
     *
     * 返回的ResponseBody 可以写对应的解析实体类，也可以在解析出来的时候再转为对应的实体类
     */
    @GET("jyptdbctl/video/getVideoPage")
    fun getCall(@Query("type") type :String,@Query("curPage") curPage:String,@Query("pageSize") pageSize:String ):Call<ResponseBody>


    /**
     * <REQUEST 4>
     *
     * 关于请求体的使用
     *
     * @param path    {path的具体值}
     * @param type    {T类型的赋值对象}
     * @return 返回 ResponseBody的实体
     *
     * 效果图
     * @Path 例子  如下GET的请求方式  [与@url区别？  一个是动态在原有基础上加path,注重改变  另一个是缺省的url,注重添加]
     *
     * @Body 例子
     *
     * class T{
     *  String type;
     *  String curPage;
     *  String pageSize;
     *
     * ...geter...seter
     *
     * }
     *
     * T t = new T()
     * t.type = "1"
     * t.curPage = "1"
     * t.pageSize = "4"
     *
     * 把t 传入到下边@type 参数对象里
     *
     * path 可以动态添加到url中，请求可以以一个T 实体类型对象进行传递
     *
     * 返回的ResponseBody 可以写对应的解析实体类，也可以在解析出来的时候再转为对应的实体类
     */

    @GET("jyptdbctl/{video}/getVideoPage")
    fun <T> getCall(@Path("video") path:String,@Body  type :T):Call<ResponseBody>






    /**
     * ---------------------->>>>>>>>>>>>>>>> POST 篇 start<<<<<<<<<<<<<<<<<<<<<<-------------------------
     *
     * POST 不想多讲 与GET差不多 注解有点差异
     *
     * @QUERY  -> {@FIELD}        [特殊]   POST建议用field
     * @QUERYMAP  -> {@FIELDMAP}  [特殊]   POST建议用field
     *
     * @FormUrlEncoded   [特殊]
     * {
     *   解决 Java.lang.IllegalArgumentException: @Field parameters can only be used with form encoding. 的错误异常
     ******************************************主要和@fieldmap @Field结合使用*********************************************
     ******************************************主要和@fieldmap @Field结合使用*********************************************
     ******************************************主要和@fieldmap @Field结合使用*********************************************
     *
     *   @FormUrlEncoded
     *   @POST("users/user/question")
     *   ...
     * }
     *
     *
     * 同样 具有@path @url @header @body
     * 自己 脑补一下...
     *
     * ---------------------->>>>>>>>>>>>>>>> POST 篇 end<<<<<<<<<<<<<<<<<<<<<<-------------------------
     */


    /**
     * <RESPONSE 1>
     *
     * POST 请求，动态url ,传一个实体对象T [USer 对象]
     *
     *@param url  可以自由定义
     *@param requestBody 自由定义类型[ 包括对象，也可以包括json字符串  USer类型为T,把字符串封装在RequestBody对象里面]
     * {
     *  RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),"你要传递的json字符串");
     * ...
     * .postServer(url,body)
     * ...
     * }
     *@return   返回ResponseBody
     *
     *开启方式
     *
     * ...
     * .postServer(url,new USer("a","b","c))
     * ...
     *
     *
     */
    @POST
    @Headers("Content-Type:application/json")
    fun postServer(@Url url: String, @Body requestBody: USer): Call<ResponseBody>


    @FormUrlEncoded
    @POST("user/login")
    fun postServer(@FieldMap map :Map<String, String>): Call<ResponseBody>

    @FormUrlEncoded
    @POST("user/login")
    fun postServer(@Field("username") username :String,@Field("password") password :String,@Field("ly") ly :String,@Field("appVersion") appVersion :String): Call<ResponseBody>
}