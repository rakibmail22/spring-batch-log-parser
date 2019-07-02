package com.ef;

import com.ef.config.DBConfig;
import com.ef.config.RunParamConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DBConfig.class, RunParamConfig.class})
public class ParserApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
	public void loadDb() {
        List<Integer> testDb = jdbcTemplate.query("SELECT 1 FROM DUAL", (rs, rowNum) -> rs.getInt("1"));
        assert testDb.get(0) == 1;
    }

}
