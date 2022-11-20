import com.ry.config.XmlConfigBuild;
import com.ry.entity.User;
import com.ry.io.Resources;
import com.ry.mapper.UserMapper;
import com.ry.pojo.Configuration;
import com.ry.sqlSession.DefaultSqlSessionFactory;
import com.ry.sqlSession.SqlSession;
import org.dom4j.DocumentException;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ryang
 * @Description
 * @date 2022年08月30日 9:21 上午
 */
public class Test {

    public static void main(String[] args) throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        Configuration configuration = new XmlConfigBuild().parseConfig(inputStream);

        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        SqlSession sqlSession = defaultSqlSessionFactory.openSession();

//        List<User> userList = sqlSession.selectList("com.ry.mapper.UserMapper.findAll");
//        System.out.println(userList);

        // 使用代理方式访问
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> all = userMapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }


    }
}
