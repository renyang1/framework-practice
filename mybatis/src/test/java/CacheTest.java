import com.ry.entity.User;
import com.ry.mapper.UserMapper1;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author ryang
 * @Description
 * @date 2022年09月02日 6:54 下午
 */
public class CacheTest {

    private UserMapper1 userMapper1;
    private SqlSession sqlSession;
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sqlSessionFactory.openSession(true);
        userMapper1 = sqlSession.getMapper(UserMapper1.class);
    }

    @Test
    public void selectUserById() {
        User user1 = userMapper1.findUserById(1);
        System.out.println(user1);

        user1.setUsername("ry_test");
        userMapper1.updateUser(user1);

        User user2 = userMapper1.findUserById(1);
        System.out.println(user2);
        System.out.println("user1 == user2：" + (user1 == user2));
    }
}
