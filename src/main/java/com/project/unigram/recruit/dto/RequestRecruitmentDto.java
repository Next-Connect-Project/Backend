package com.project.unigram.recruit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.unigram.recruit.domain.Personnel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class RequestRecruitmentDto {
	
	@NotEmpty(message = "NOT_EMPTY:카테고리(category)은 null이거나 공백이어선 안됩니다.")
	@Pattern(regexp = "PROJECT|STUDY", message = "WRONG_TYPE:PROJECT 혹은 STUDY로 넣어주세요")
	private String category;
	
	@NotBlank(message = "NOT_EMTPY:제목(title)은 null이거나 공백이어선 안됩니다.")
	private String title;
	
	@Future(message = "NOT_PAST:마감일이 과거여서는 안됩니다.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime deadline;
	
	@Size(max = 26, message = "OUT_OF_RANGE:tech 배열의 범위가 벗어났습니다. 0 - 26")
	private String[] tech;
	
	@Valid
	private Personnel personnel;
	
	@NotBlank(message = "NOT_EMPTY:목적(purpose)은 null이거나 공백이어선 안됩니다.")
	private String purpose;
	
	@NotBlank(message = "NOT_EMPTY:progress은 null이거나 공백이어선 안됩니다.")
	private String progress;
	
	@NotBlank(message = "NOT_EMPTY:연락(contact)은 null이거나 공백이어선 안됩니다.")
	private String contact;
	
	@NotBlank(message = "NOT_EMPTY:진행 기간(duration)은 null이거나 공백이어선 안됩니다.")
	private String duration;
	
	@NotBlank(message = "NOT_EMPTY:시간 및 장소(timeandplace)는 null이거나 공백이어선 안됩니다.")
	private String timeandplace;
	
	@NotBlank(message = "NOT_EMPTY:진행 방식(way)은 null이거나 공백이어선 안됩니다.")
	private String way;
	
	private String free;

}
