import com.paytm.insider.api.HackernewsApiApplication;
import com.paytm.insider.service.business.CommentService;
import com.paytm.insider.service.dao.HNCommentDao;
import com.paytm.insider.service.dao.HNStoryDao;
import com.paytm.insider.service.entity.HNCommentEntity;
import com.paytm.insider.service.entity.HNStoryEntity;
import com.paytm.insider.service.entity.HNUserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackernewsApiApplication.class)
@AutoConfigureMockMvc
public class CommentControllerTests {

    @Autowired
    private MockMvc mvc;

    @Mock
    HNCommentDao commentDao;

    @Mock
    HNStoryDao storyDao;

    @MockBean
    CommentService commentService;

    /***
     * Test for successful comment fetch
     */
    @Test
    public void successfulCommentsFetch() throws Exception {
        HNUserEntity user = new HNUserEntity();
        user.setHn_age(12);
        user.setUser_handle("sha_gun");

        HNStoryEntity story = new HNStoryEntity();
        story.setId(1234);
        story.setTitle("Random title");
        story.setUrl("abc.com");
        story.setScore(234);
        story.setSubmission_time(ZonedDateTime.now());
        story.setUser(user);
        story.setCreated_at(ZonedDateTime.now());

        HNCommentEntity commentEntity = new HNCommentEntity();
        commentEntity.setStory(story);
        commentEntity.setComment_count(2);
        commentEntity.setComment_time(ZonedDateTime.now());
        commentEntity.setComment_text("Random text");
        commentEntity.setUser(user);
        commentEntity.setId(2345);
        List<HNCommentEntity> hnCommentEntities = new ArrayList<>();
        hnCommentEntities.add(commentEntity);

        when(storyDao.getStoryById(any(Integer.class))).thenReturn(story);
        when(commentDao.getCommentlistByCount(any(HNStoryEntity.class))).thenReturn(hnCommentEntities);

        mvc.perform(MockMvcRequestBuilders.get("/comments/1234"))
                .andExpect(status().isOk());
    }

    /***
     * Test for unsuccessful comment fetch on null ID
     */
    @Test
    public void unSuccessfulCommentsIDNotGiven() throws Exception {
        //HNCommentDao commentDao = mock(HNCommentDao.class);

        List<HNCommentEntity> hnCommentEntities = new ArrayList<>();
        when(commentDao.getCommentlistByCount(any(HNStoryEntity.class))).thenReturn(hnCommentEntities);
        mvc.perform(MockMvcRequestBuilders.get("/comments/"+null))
                .andExpect(status().isBadRequest());
    }

}
