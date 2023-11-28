package com.leagueofrestaurant.web.member.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leagueofrestaurant.web.report.domain.Type;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @GeneratedValue
    @Id
    @Column(name = "member_id")
    private long id;
    @NotBlank(message = "이름을 작성하세요.")
    private String name;
    @NotBlank(message = "핸드폰 번호를 작성하세요.")
    private String phoneNumber;
    @NotBlank(message = "비밀번호를 작성하세요.")
    private String password;

    @NotNull(message = "성별을 입력하세요.")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotNull(message = "생일을 입력하세요.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @NotNull(message = "member type 을 입력하세요.")
    @Enumerated(EnumType.STRING)
    private MemberType type;
    private boolean isDeleted;

    public Member(String name, String phoneNumber, String password,
                  Gender gender, LocalDate birthday, MemberType type) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.type = type;
        this.isDeleted = false;
    }

    /**
     * 유저 정보 변경
     */
    public void changeName(String name) {
        this.name = name;
    }
    public void changePassword(String password) {
        this.password = password;
    }
    public void changeGender(Gender gender){
        this.gender = gender;
    }
    public void changeBirthday(LocalDate birthday){
        this.birthday =birthday;
    }
    public void changeType(MemberType type){
        this.type = type;
    }
    public void softDeleted(){
        this.isDeleted = true;
    }
}
