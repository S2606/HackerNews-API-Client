import com.paytm.insider.api.HackernewsApiApplication;
import com.paytm.insider.service.business.StoryService;
import com.paytm.insider.service.dao.HNStoryDao;
import com.paytm.insider.service.entity.HNStoryEntity;
import com.paytm.insider.service.entity.HNUserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackernewsApiApplication.class)
@AutoConfigureMockMvc
public class StoryControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    StoryService storyService;

    /***
     * Test for successful story fetch
     */
    @Test
    public void successfulDetailsFetch() throws Exception {
        HNStoryDao storyDao = mock(HNStoryDao.class);
        HNUserEntity user = new HNUserEntity();
        user.setHn_age(12);
        user.setUser_handle("sha_gun");

        HNStoryEntity storyEntity = new HNStoryEntity();
        storyEntity.setId(1234);
        storyEntity.setTitle("Random title");
        storyEntity.setUrl("abc.com");
        storyEntity.setScore(234);
        storyEntity.setSubmission_time(ZonedDateTime.now());
        storyEntity.setUser(user);
        storyEntity.setCreated_at(ZonedDateTime.now());
        List<HNStoryEntity> hnStoryEntities = new ArrayList<>();
        hnStoryEntities.add(storyEntity);
        when(storyDao.getStoryListByRange(any(ZonedDateTime.class))).thenReturn(hnStoryEntities);
        mvc.perform(MockMvcRequestBuilders.get("/top-stories"))
                .andExpect(status().isOk());
    }

}
