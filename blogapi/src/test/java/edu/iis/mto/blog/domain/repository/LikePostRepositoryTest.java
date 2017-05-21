package edu.iis.mto.blog.domain.repository;

import java.util.List;

import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.domain.BlogManager;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {
	
    private BlogManager entityManager;

    @Autowired
    private LikePostRepository likePostRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BlogPostRepository blogPostRepository;

    private LikePost likePost;
    private User user;
    private BlogPost blogPost;
    
    @Test
    public void shouldCreateOnePost() {
    	List<LikePost> likePosts = likePostRepository.findAll();
    	
    	Assert.assertThat(likePosts, Matchers.hasSize(1));
    }
    
    @Test
    public void shouldModifyPostEntry() {
    	List<LikePost> likePosts = likePostRepository.findAll();
    	String oldEntry = likePosts.get(0).getPost().getEntry();
    	String newEntry = "new blog post entry";
    	likePosts.get(0).getPost().setEntry(newEntry); 	

    	Assert.assertThat(likePosts.get(0).getPost().getEntry(), Matchers.equalTo(newEntry));
    }
    
    @Test
    public void shouldFindLikePostByUserAndPost() {
    	List<BlogPost> foundBlogPosts = blogPostRepository.findAll();
    	List<User> foundUsers = userRepository.findAll();
    	Optional<LikePost> likePost = likePostRepository.findByUserAndPost(foundUsers.get(0), foundBlogPosts.get(0));
    	List<LikePost> likePosts = likePostRepository.findAll();
    	Assert.assertThat(likePost.get(), Matchers.equalTo(likePosts.get(0)));
    }
}

