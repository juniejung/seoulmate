package seoulmate.project.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import seoulmate.project.domain.Book;
import seoulmate.project.domain.Inquiry;

@Repository
public class JdbcTemplateRepository implements SeoulRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplateRepository(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	private RowMapper<Inquiry> inquiryRowMapper() {
		return (ResultSet rs, int rowNum) -> {
			Inquiry inquiry = new Inquiry();
			inquiry.setId(rs.getLong("id"));
			inquiry.setBookingId(rs.getLong("bookingId"));
			inquiry.setName(rs.getString("name"));
			inquiry.setPhone1(rs.getString("phone1"));
			inquiry.setPhone2(rs.getString("phone2"));
			inquiry.setPhone3(rs.getString("phone3"));
			inquiry.setTitle(rs.getString("title"));
			inquiry.setContent(rs.getString("content"));
			return inquiry;
		};
	}

	private RowMapper<Book> bookRowMapper() {
		return (ResultSet rs, int rowNum) -> {
			Book book = new Book();
			book.setId(rs.getLong("id"));
			book.setName(rs.getString("name"));
			book.setPhone1(rs.getString("phone1"));
			book.setPhone2(rs.getString("phone2"));
			book.setPhone3(rs.getString("phone3"));
			book.setTime(rs.getInt("time"));
			book.setDestination(rs.getString("destination"));
			book.setDate(rs.getString("date"));
			book.setNumber(rs.getInt("number"));
			return book;
		};
	}

	@Override
	public Inquiry saveInquiry(Inquiry inquiry) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("inquiry")
				.usingGeneratedKeyColumns("id");
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("bookingId", inquiry.getBookingId());
		parameters.put("name", inquiry.getName());
		parameters.put("phone1", inquiry.getPhone1());
		parameters.put("phone2", inquiry.getPhone2());
		parameters.put("phone3", inquiry.getPhone3());
		parameters.put("title", inquiry.getTitle());
		parameters.put("content", inquiry.getContent());
		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		inquiry.setId(key.longValue());
		return inquiry;
	}

	@Override
	public List<Inquiry> findByNameInquiry(String name) {
		String query = "select *from inquiry where name like ?";
		List<Inquiry> result = jdbcTemplate.query(query, inquiryRowMapper(), "%" + name + "%");
		return result;
	}

	@Override
	public List<Inquiry> findAllInquiry() {
		return jdbcTemplate.query("select * from inquiry limit 5", inquiryRowMapper());
	}

	@Override
	public Book saveBook(Book book) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("book")
				.usingGeneratedKeyColumns("id");
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", book.getName());
		parameters.put("phone1", book.getPhone1());
		parameters.put("phone2", book.getPhone2());
		parameters.put("phone3", book.getPhone3());
		parameters.put("time", book.getTime());
		parameters.put("destination", book.getDestination());
		parameters.put("date", book.getDate());
		parameters.put("number", book.getNumber());
		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		book.setId(key.longValue());
		return book;
	}

	@Override
	public int getBookingCount(Book book) {
		String destination = book.getDestination();
		int time = book.getTime();
		String date = book.getDate();
		String query = "select sum(number) from book where destination like ? and time=? and date like ?";
		int bookingCount = jdbcTemplate.query(query, new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getInt(1);
				} else {
					return null;
				}
			}
		}, destination, time, date);
		return bookingCount;
	}

	@Override
	public List<Book> findByNameBook(String name) {
		String query = "select * from book where name like ?";
		List<Book> result = jdbcTemplate.query(query, bookRowMapper(), "%" + name + "%");
		return result;
	}

	@Override
	public List<Book> findAllBook() {
		return jdbcTemplate.query("select * from book limit 5", bookRowMapper());
	}

	@Override
	public Optional<Book> findByIdBook(Long id) {
		String query = "select *from book where id=?";
		List<Book> result = jdbcTemplate.query(query, bookRowMapper(), id);
		return result.stream().findAny();
	}

	@Override
	public void deleteBook(Long id) {
		jdbcTemplate.update("delete from book where id= ?", id);
	}

	@Override
	public void clearBeforeDate() {
		jdbcTemplate.update("delete from book where date < curdate()");
	}
}
