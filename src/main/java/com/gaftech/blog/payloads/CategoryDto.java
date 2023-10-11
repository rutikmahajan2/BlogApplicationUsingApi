package com.gaftech.blog.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryDto {

	private Integer categoryId;

	@NotEmpty
	@Size(min = 5)
	private String categoryTitle;

	@NotEmpty
	@Size(min = 15)
	private String categoryDescription;
}
