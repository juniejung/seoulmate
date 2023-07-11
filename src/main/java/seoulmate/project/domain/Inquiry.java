package seoulmate.project.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inquiry {
	private Long id;
	private Long bookingId;
	private String name;
	private String phone1;
	private String phone2;
	private String phone3;
	private String title;
	private String content;

	public Inquiry() {}

	public Inquiry(String name, String phone1, String phone2, String phone3, String title, String content) {
		this.name = name;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.phone3 = phone3;
		this.title = title;
		this.content = content;
	}
}
