import com.leon.mybatis.entity.Factory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class application {

    public static void main(String[] args) throws IOException {
        SqlSessionFactory sqlSessionFactory = null;
        //读取配置文件
        Reader reader= Resources.getResourceAsReader("mybatis-config.xml");
        //通过配置文件构建会话工厂类
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        SqlSession session = null;
        try{
            session = sqlSessionFactory.openSession();
            List<Factory> factory_list = session.selectList("factory.findAll");
            for (Factory factory:factory_list) {
                System.out.println(factory);
                System.out.println(String.format("%-10s", factory.getName()) + ' ' + factory.getAdCode());
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally{
            if (session != null){
                // 将Connection归还连接池供其他session使用
                session.close();
            }
        }
    }

}
