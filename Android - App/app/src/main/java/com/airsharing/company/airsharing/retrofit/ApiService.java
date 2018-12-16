package com.airsharing.company.airsharing.retrofit;

import com.airsharing.company.airsharing.model.AirData;
import com.airsharing.company.airsharing.model.Contents;
import com.airsharing.company.airsharing.model.MemberData;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by surin on 2017. 10. 14..
 */

public interface ApiService {

    // 네이버 아이디로 로그인
    @FormUrlEncoded
    @POST("/mobile/member/naver/login")
    Call<ResponseBody> login_naver(@Field("name") String name, @Field("email") String email,
                                   @Field("gender") String gender, @Field("birthdate") String birthdate, @Field("naver_id") String naver_id);

    // 일반 로그인
    @FormUrlEncoded
    @POST("/mobile/member/login")
    Call<ResponseBody> login(@Field("id") String id, @Field("pw") String pw);


    // 회원 가입
    @FormUrlEncoded
    @POST("/mobile/member/registeration")   // 회원 가입
    Call<MemberData> signup(@Field("id") String id, @Field("pw") String pw, @Field("name") String name, @Field("email") String email,
                            @Field("phone") String phone, @Field("gender") String gender);

    // 아이디 찾기
    @FormUrlEncoded
    @POST("/mobile/member/request/id")    // 아이디 찾기
    Call<List<MemberData>> findid(@Field("name") String name, @Field("email") String email);


    // 비밀번호 찾기
    @FormUrlEncoded
    @POST("/mobile/member/request/passwd")   // 비밀번호 찾기
    Call<List<MemberData>> findpw(@Field("userid") String userid, @Field("email") String email);

    // 탈퇴
    @FormUrlEncoded
    @POST("/mobile/member/withdrawal")
    Call<MemberData> withdrawal(@Field("userid") String id);

    // 실외 IoT 측정 값 가져오기
    @FormUrlEncoded
    @POST("/device/measurement/outside")
    Call<List<AirData>> requestOutsidedb(@Field("userid") String userid, @Field("longitude") String longitude,
                                         @Field("latitude") String latitude);

    //개인정보 가져오기
    @GET("/mobile/setting/info/{userid}")
    Call<List<MemberData>> getInfo(@Path("userid") String userid);

    @FormUrlEncoded
    //개인정보 변경
    @PUT("/mobile/setting/modification/{userid}")
    Call<MemberData> updateInfo(@Path("userid") String userid, @Field("name") String name, @Field("email") String email,
                                @Field("phone") String phone);
    //패스워드 변경
    @FormUrlEncoded
    @PUT("/mobile/setting/modification/passwd/{userid}")
    Call<MemberData> updatePw(@Path("userid") String userid, @Field("passwd") String passwd);

    //알람 온오프 설정
    @PUT("/mobile/setting/alarm/{alarm}/{userid}")
    Call<MemberData> updateAlarm(@Path("alarm") Boolean alarm, @Path("userid") String userid);

    // 시/도별 미세먼지 데이터 가져오기
    @GET("/mobile/opendata/area/dust")
    Call<ArrayList> get_dust();

    // 사용자 위치 기반 미세먼지 데이터 가져오기
    @GET("/mobile/opendata/areabased/dust/{uuid}")
    Call<List<AirData>> get_userArea(@Path("uuid") String userid);

    // 커뮤니티 게시글 생성
    @FormUrlEncoded
    @POST("/community/comment")
    Call<Void> upload(@Field("uuid") String uuid, @Field("content") String content, @Field("region") String region, @Field("detailedRegion") String detailedRegion);

    // 커뮤니티 게시글 가져오기
    @GET("/community/comment/{region}/{detailedRegion}")
    Call<List<Contents>> getContents(@Path("region") String region, @Path("detailedRegion") String detailedRegion);
}
