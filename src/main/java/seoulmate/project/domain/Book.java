package seoulmate.project.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Book {
	private Long id;
	private String name;
	private String phone1;
	private String phone2;
	private String phone3;
	private int time;
	private String destination;
	private String date;
	private int number;

	public Book(String name, String phone1, String phone2, String phone3, int time, String destination, String date,
			int number) {
		this.name = name;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.phone3 = phone3;
		this.time = time;
		this.destination = destination;
		this.date = date;
		this.number = number;
	}
}
