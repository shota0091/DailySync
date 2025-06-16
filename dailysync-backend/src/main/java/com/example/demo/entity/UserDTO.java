package com.example.demo.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDTO {

		@NotBlank(message="名前は必須です")
	    @Size(min = 2, max = 20, message = "名前は2〜20文字で入力してください")
	    private String name;

	    @NotBlank(message="パスワードは必須です")
	    @Size(min = 8, message = "パスワードは8文字以上で入力してください")
	    private String password;
}
