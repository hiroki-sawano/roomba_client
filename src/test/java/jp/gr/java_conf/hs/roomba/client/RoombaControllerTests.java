/**
 * 
 */
package jp.gr.java_conf.hs.roomba.client;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;

import jp.gr.java_conf.hs.roomba.client.repositories.MyDataRepository;

/**
 * @author macbookair
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RoombaController.class)
public class RoombaControllerTests {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private MyDataRepository repo;
	@MockBean
	private Settings settings;
	@MockBean
	private Roomba roomba;

	private Command c1 = new Command("c1", "seq1");
	private Command c2 = new Command("c2", "seq2");

	private Map<String, String> selectItems = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("foo", "fooVal");
			put("bar", "barVal");
		}
	};

	@Before
	public void before() {
		given(this.roomba.connect("192.168.0.20", 9001)).willReturn(true);
		given(this.roomba.send("128")).willReturn(true);
		given(this.roomba.disconnect()).willReturn(true);
	}

	@Test
	public void index() throws Exception {
		List<Command> commandList = new ArrayList<Command>();
		commandList.add(c1);
		commandList.add(c2);
		given(this.settings.getSequences()).willReturn(selectItems);
		given(this.repo.findAll()).willReturn(commandList);

		MvcResult mvcResult = this.mvc.perform(get("/").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andExpect(view().name("index")).andExpect(model().attributeExists("userInput"))
				.andExpect(model().attribute("dataTable", commandList)).andReturn();
		ModelMap modelMap = mvcResult.getModelAndView().getModelMap();
		Object uio = modelMap.get("userInput");
		assertThat(uio, is(not(nullValue())));
		assertThat(uio, is(instanceOf(UserInput.class)));
		UserInput ui = (UserInput) uio;
		assertThat(ui.getSelectItems(), is(selectItems));
	}

	@Test
	public void addCommand() throws Exception {

		MvcResult mvcResult = this.mvc.perform(post("/invoke").accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andReturn();
			//	.andExpect(view().name("index")).andExpect(model().attributeExists("userInput"))
			//	.andExpect(model().attribute("dataTable", commandList)).andReturn();

	}

	@Test
	public void execCommandInUserInput() throws Exception {

	}

	@Test
	public void execAllCommands() throws Exception {

	}

	@Test
	public void execCommandSpecifiedById() throws Exception {

	}

	@Test
	public void edit() throws Exception {

	}

	@Test
	public void update() throws Exception {

	}

	@Test
	public void delete() throws Exception {

	}
}
