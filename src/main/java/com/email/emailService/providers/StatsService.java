package com.email.emailService.providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.email.emailService.model.StatsUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatsService {
	
	private final String url = "jdbc:mysql://localhost:3306/securitydb";
	private final String user = "root";
	private final String key = "";
	
	public List<StatsUser> getStats() {
		List<StatsUser> list = new ArrayList<StatsUser>();
		LocalDateTime datetime = LocalDateTime.now();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, user, key);
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT from_email, COUNT(*) 'Emails Sent' FROM email WHERE SUBSTRING(date_of_email, 1, 2) = '"+ String.valueOf(datetime.getDayOfMonth()) +"' GROUP BY from_email;");
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String username = resultSet.getString(1);
				Integer emailsSent = resultSet.getInt(2);
				list.add(new StatsUser(username, emailsSent));
			}
			connection.close();
			preparedStatement.close();
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return list;
	}
}
