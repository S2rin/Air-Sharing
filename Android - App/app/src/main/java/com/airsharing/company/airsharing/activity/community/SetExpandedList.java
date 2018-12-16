package com.airsharing.company.airsharing.activity.community;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by hyunju on 2017-11-06.
 */

public class SetExpandedList {

    public static LinkedHashMap<String, List<String>> getData() {

        LinkedHashMap<String, List<String>> list = new LinkedHashMap<>();

        List<String> seoul = new ArrayList<>();
        List<String> gyeonggi = new ArrayList<>();
        List<String> incheon = new ArrayList<>();
        List<String> daegu = new ArrayList<>();
        List<String> gwangju = new ArrayList<>();
        List<String> gangwon = new ArrayList<>();
        List<String> daejeon = new ArrayList<>();
        List<String> ulsan = new ArrayList<>();
        List<String> chungbuk = new ArrayList<>();
        List<String> chungnam = new ArrayList<>();
        List<String> jeonbuk = new ArrayList<>();
        List<String> jeonnam = new ArrayList<>();
        List<String> gyeongbuk = new ArrayList<>();
        List<String> gyeongnam = new ArrayList<>();
        List<String> busan = new ArrayList<>();
        List<String> jeju = new ArrayList<>();
        List<String> sejong = new ArrayList<>();

        seoul.add("강남");
        seoul.add("강동");
        seoul.add("강북");
        seoul.add("강서");

        gyeonggi.add("과천•의왕");
        gyeonggi.add("광명•시흥");
        gyeonggi.add("부천");
        gyeonggi.add("수원");
        gyeonggi.add("안산");
        gyeonggi.add("안양•군포");
        gyeonggi.add("평택");
        gyeonggi.add("화성•오산");
        gyeonggi.add("가평•양평•남양주");
        gyeonggi.add("광주");
        gyeonggi.add("구리•하남");
        gyeonggi.add("기흥•신길•동백");
        gyeonggi.add("분당•판교");
        gyeonggi.add("성남•모란");
        gyeonggi.add("안성");
        gyeonggi.add("여주•이천");
        gyeonggi.add("죽전•수지");
        gyeonggi.add("고양•일산");
        gyeonggi.add("김포");
        gyeonggi.add("양주•동두천");
        gyeonggi.add("의정부");
        gyeonggi.add("포천•연천");
        gyeonggi.add("파주");

        incheon.add("계양•서구");
        incheon.add("남구•동구");
        incheon.add("남동구");
        incheon.add("부평");
        incheon.add("연수구");
        incheon.add("중구•강화");

        daejeon.add("대덕구");
        daejeon.add("동구");
        daejeon.add("서구");
        daejeon.add("유성구");
        daejeon.add("중구•서대전");

        daegu.add("달서구•서구");
        daegu.add("달성군");
        daegu.add("동구•팔공");
        daegu.add("북구•칠곡");

        busan.add("강서구");
        busan.add("경성대•광안");
        busan.add("영도•남포");
        busan.add("부산대•동래");
        busan.add("영도•남포");
        busan.add("사상•북구");
        busan.add("서구•사하");
        busan.add("서면•동구");
        busan.add("연산•거제");
        busan.add("해운대•기장");

        ulsan.add("남구•울산대");
        ulsan.add("동구");
        ulsan.add("북구");
        ulsan.add("중구•울주군");

        gwangju.add("광산구");
        gwangju.add("남구•동구");
        gwangju.add("북구");
        gwangju.add("서구");

        gangwon.add("강릉•속초");
        gangwon.add("고성•인제");
        gangwon.add("동해•삼척");
        gangwon.add("영월•태백");
        gangwon.add("원주•평창");
        gangwon.add("춘천•흥천");
        gangwon.add("화천•양구");

        sejong.add("세종");

        chungbuk.add("괴산•증평");
        chungbuk.add("단양•제천");
        chungbuk.add("보은•영동•옥천");
        chungbuk.add("진천•음성");
        chungbuk.add("청주");
        chungbuk.add("충주");

        chungnam.add("논산•금산•계룡");
        chungnam.add("당진");
        chungnam.add("보령•서천");
        chungnam.add("부여•공주");
        chungnam.add("서산•태안");
        chungnam.add("천안•아산");
        chungbuk.add("홍성•예산•청양");

        gyeongbuk.add("경산•청도");
        gyeongbuk.add("경주•포항남구");
        gyeongbuk.add("고령•성주•칠곡");
        gyeongbuk.add("김천•구미");
        gyeongbuk.add("문경•예천•영주");
        gyeongbuk.add("상주");
        gyeongbuk.add("안동•의성•청송");
        gyeongbuk.add("영덕•포항북구");
        gyeongbuk.add("영양•울진•봉화");
        gyeongbuk.add("영천•군위");

        gyeongnam.add("김해•장유");
        gyeongnam.add("밀양•양산");
        gyeongnam.add("의령•창녕•함안");
        gyeongnam.add("진주•사천");
        gyeongnam.add("창원•마산•진해");
        gyeongnam.add("통영•거제");
        gyeongnam.add("하동•남해");
        gyeongnam.add("합천•거창 외");

        jeonbuk.add("고창•부안•정읍");
        jeonbuk.add("군산•익산");
        jeonbuk.add("남원•순창•임실");
        jeonbuk.add("무수•장수•진안");
        jeonbuk.add("전주•완주•김제");


        jeonnam.add("곡성•구례");
        jeonnam.add("나주•함평•무안");
        jeonnam.add("목포•신안");
        jeonnam.add("보성•고흥•화순");
        jeonnam.add("여수•순천•광양");
        jeonnam.add("영광•장성•담양");
        jeonnam.add("영암•강진•장흥");
        jeonnam.add("해남•진도•완도");

        jeju.add("서귀포시");
        jeju.add("제주시");

        list.put("서울", seoul);
        list.put("경기",gyeonggi);
        list.put("인천", incheon);
        list.put("대전", daejeon);
        list.put("대구", daegu);
        list.put("부산", busan);
        list.put("울산", ulsan);
        list.put("광주", gwangju);
        list.put("강원", gangwon);
        list.put("세종", sejong);
        list.put("충북", chungbuk);
        list.put("충남", chungnam);
        list.put("경북", gyeongbuk);
        list.put("경남", gyeongnam);
        list.put("전북", jeonbuk);
        list.put("전남", jeonnam);
        list.put("제주", jeju);

        return list;
    }

    public static LinkedHashMap<String, List<String>> getData_spinner() {

        LinkedHashMap<String, List<String>> list = new LinkedHashMap<>();

        List<String> seoul = new ArrayList<>();
        List<String> gyeonggi = new ArrayList<>();
        List<String> incheon = new ArrayList<>();
        List<String> daegu = new ArrayList<>();
        List<String> gwangju = new ArrayList<>();
        List<String> gangwon = new ArrayList<>();
        List<String> daejeon = new ArrayList<>();
        List<String> ulsan = new ArrayList<>();
        List<String> chungbuk = new ArrayList<>();
        List<String> chungnam = new ArrayList<>();
        List<String> jeonbuk = new ArrayList<>();
        List<String> jeonnam = new ArrayList<>();
        List<String> gyeongbuk = new ArrayList<>();
        List<String> gyeongnam = new ArrayList<>();
        List<String> busan = new ArrayList<>();
        List<String> jeju = new ArrayList<>();
        List<String> sejong = new ArrayList<>();
        List<String> sido = new ArrayList<>();

        seoul.add("선택하세요");
        seoul.add("강남");
        seoul.add("강동");
        seoul.add("강북");
        seoul.add("강서");

        gyeonggi.add("선택하세요");
        gyeonggi.add("과천•의왕");
        gyeonggi.add("광명•시흥");
        gyeonggi.add("부천");
        gyeonggi.add("수원");
        gyeonggi.add("안산");
        gyeonggi.add("안양•군포");
        gyeonggi.add("평택");
        gyeonggi.add("화성•오산");
        gyeonggi.add("가평•양평•남양주");
        gyeonggi.add("광주");
        gyeonggi.add("구리•하남");
        gyeonggi.add("기흥•신길•동백");
        gyeonggi.add("분당•판교");
        gyeonggi.add("성남•모란");
        gyeonggi.add("안성");
        gyeonggi.add("여주•이천");
        gyeonggi.add("죽전•수지");
        gyeonggi.add("고양•일산");
        gyeonggi.add("김포");
        gyeonggi.add("양주•동두천");
        gyeonggi.add("의정부");
        gyeonggi.add("포천•연천");
        gyeonggi.add("파주");

        incheon.add("선택하세요");
        incheon.add("계양•서구");
        incheon.add("남구•동구");
        incheon.add("남동구");
        incheon.add("부평");
        incheon.add("연수구");
        incheon.add("중구•강화");

        daejeon.add("선택하세요");
        daejeon.add("대덕구");
        daejeon.add("동구");
        daejeon.add("서구");
        daejeon.add("유성구");
        daejeon.add("중구•서대전");

        daegu.add("선택하세요");
        daegu.add("달서구•서구");
        daegu.add("달성군");
        daegu.add("동구•팔공");
        daegu.add("북구•칠곡");

        busan.add("선택하세요");
        busan.add("강서구");
        busan.add("경성대•광안");
        busan.add("영도•남포");
        busan.add("부산대•동래");
        busan.add("영도•남포");
        busan.add("사상•북구");
        busan.add("서구•사하");
        busan.add("서면•동구");
        busan.add("연산•거제");
        busan.add("해운대•기장");

        ulsan.add("선택하세요");
        ulsan.add("남구•울산대");
        ulsan.add("동구");
        ulsan.add("북구");
        ulsan.add("중구•울주군");

        gwangju.add("선택하세요");
        gwangju.add("광산구");
        gwangju.add("남구•동구");
        gwangju.add("북구");
        gwangju.add("서구");

        gangwon.add("선택하세요");
        gangwon.add("강릉•속초");
        gangwon.add("고성•인제");
        gangwon.add("동해•삼척");
        gangwon.add("영월•태백");
        gangwon.add("원주•평창");
        gangwon.add("춘천•흥천");
        gangwon.add("화천•양구");

        sejong.add("선택하세요");
        sejong.add("세종");

        chungbuk.add("선택하세요");
        chungbuk.add("괴산•증평");
        chungbuk.add("단양•제천");
        chungbuk.add("보은•영동•옥천");
        chungbuk.add("진천•음성");
        chungbuk.add("청주");
        chungbuk.add("충주");

        chungnam.add("선택하세요");
        chungnam.add("논산•금산•계룡");
        chungnam.add("당진");
        chungnam.add("보령•서천");
        chungnam.add("부여•공주");
        chungnam.add("서산•태안");
        chungnam.add("천안•아산");
        chungbuk.add("홍성•예산•청양");

        gyeongbuk.add("선택하세요");
        gyeongbuk.add("경산•청도");
        gyeongbuk.add("경주•포항남구");
        gyeongbuk.add("고령•성주•칠곡");
        gyeongbuk.add("김천•구미");
        gyeongbuk.add("문경•예천•영주");
        gyeongbuk.add("상주");
        gyeongbuk.add("안동•의성•청송");
        gyeongbuk.add("영덕•포항북구");
        gyeongbuk.add("영양•울진•봉화");
        gyeongbuk.add("영천•군위");

        gyeongnam.add("선택하세요");
        gyeongnam.add("김해•장유");
        gyeongnam.add("밀양•양산");
        gyeongnam.add("의령•창녕•함안");
        gyeongnam.add("진주•사천");
        gyeongnam.add("창원•마산•진해");
        gyeongnam.add("통영•거제");
        gyeongnam.add("하동•남해");
        gyeongnam.add("합천•거창 외");

        jeonbuk.add("선택하세요");
        jeonbuk.add("고창•부안•정읍");
        jeonbuk.add("군산•익산");
        jeonbuk.add("남원•순창•임실");
        jeonbuk.add("무수•장수•진안");
        jeonbuk.add("전주•완주•김제");

        jeonnam.add("선택하세요");
        jeonnam.add("곡성•구례");
        jeonnam.add("나주•함평•무안");
        jeonnam.add("목포•신안");
        jeonnam.add("보성•고흥•화순");
        jeonnam.add("여수•순천•광양");
        jeonnam.add("영광•장성•담양");
        jeonnam.add("영암•강진•장흥");
        jeonnam.add("해남•진도•완도");

        jeju.add("선택하세요");
        jeju.add("서귀포시");
        jeju.add("제주시");

        sido.add("");

        list.put("시/도", sido);
        list.put("서울", seoul);
        list.put("경기",gyeonggi);
        list.put("인천", incheon);
        list.put("대전", daejeon);
        list.put("대구", daegu);
        list.put("부산", busan);
        list.put("울산", ulsan);
        list.put("광주", gwangju);
        list.put("강원", gangwon);
        list.put("세종", sejong);
        list.put("충북", chungbuk);
        list.put("충남", chungnam);
        list.put("경북", gyeongbuk);
        list.put("경남", gyeongnam);
        list.put("전북", jeonbuk);
        list.put("전남", jeonnam);
        list.put("제주", jeju);

        return list;
    }
}
