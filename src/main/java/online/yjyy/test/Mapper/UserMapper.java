package online.yjyy.test.Mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import online.yjyy.test.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USER")
    Page<User> getUserList();

    @Select("SELECT * FROM USER")
   List<User> getUserPageInfo();


    @Select("SELECT * FROM USER")
    List<User> queryUserList();
}
