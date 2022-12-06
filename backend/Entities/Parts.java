import DBS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.math.BigDecimal;
import java.math.BigInteger;

/*
    part_number serial primary key,
    customer_id bigint unsigned,
    part_name_id bigint unsigned,
    category_id bigint unsigned,
    drawing_id bigint unsigned,
    rubber smallint,
    diameter_AT decimal,
    diameter_AT_tol varchar(10),
    length_L_AT decimal,
    length_L_AT_tol varchar(10),
    diameter_IT decimal,
    diameter_IT_tol varchar(10),
    length_L_IT decimal,
    length_L_IT_tol varchar(10),
    diameter_ZT decimal,
    diameter_ZT_tol varchar(10),
    length_L_ZT decimal,
    length_L_ZT_tol varchar(10),
    cr_steg mediumint,
    cr_niere smallint,
    ca smallint,
    ct decimal,
    ck decimal,
    rating int default 0
)
 */
public class Parts {

    private Integer part_number;
    private BigInteger customer_id;
    private BigInteger part_name_id;
    private BigInteger category_id;
    private BigInteger drawing_id;
    private Short rubber;
    private Double diameter_AT;
    private String diameter_AT_tol;
    private Double length_L_AT;
    private String length_L_AT_tol;
    private Double diameter_IT;
    private String diameter_IT_tol;
    private Double length_L_IT;
    private String length_L_IT_tol;
    private Double diameter_ZT;
    private String diameter_ZT_tol;
    private Double length_L_ZT;
    private String length_L_ZT_tol;
}