package jp.gr.java_conf.hs.roomba.client;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.gr.java_conf.hs.roomba.client.repositories.MyDataRepository;

@Controller
public class RoombaController {
	@Autowired
	private Settings settings;
	@Autowired
	private MyDataRepository repository;
	@Autowired
	private Roomba roomba;
	private Log log = LogFactory.getLog(RoombaController.class);

	@PostConstruct
	public void init() {
		// connect to Roomba
		roomba.connect(settings.getIp(), settings.getPort());

		// load default sequences from application.yml
		// settings.getSequences().forEach((k, v) -> repository.saveAndFlush(new
		// Command(k, v)));
	}

	@PreDestroy
	public void end() {
		roomba.disconnect();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(@ModelAttribute("userInput") UserInput userInput, ModelAndView mav) {
		userInput.setSelectItems(settings.getSequences());
		mav.setViewName("index");
		mav.addObject("userInput", userInput);
		Iterable<Command> list = repository.findAll();
		mav.addObject("dataTable", list);
		return mav;
	}

	@RequestMapping(value = "/invoke", params = "add", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView addCommand(@ModelAttribute("userInput") @Validated UserInput userInput, BindingResult result,
			ModelAndView mav) {

		ModelAndView res = null;
		Command newCommand = new Command();
		boolean isErr = false;

		if (!result.hasErrors()) {
			newCommand.setName(userInput.getName());
			if (!userInput.getSelectedSequence().isEmpty()) {
				newCommand.setSerialSequence(userInput.getSelectedSequence());
			} else if (!userInput.getArbitrarySequence().isEmpty()) {
				newCommand.setSerialSequence(userInput.getArbitrarySequence());
			} else {
				isErr = true;
			}
		} else {
			isErr = true;
		}

		if (!isErr) {
			log.info("Adding new command [" + newCommand + "]");
			repository.saveAndFlush(newCommand);
			res = new ModelAndView("redirect:/");
		} else {
			log.warn("Validation error occured [" + userInput + "]");
			userInput.setSelectItems(settings.getSequences());
			mav.setViewName("index");
			Iterable<Command> list = repository.findAll();
			mav.addObject("dataTable", list);
			res = mav;
		}
		return res;
	}

	@RequestMapping(value = "/invoke", params = "im_exec", method = RequestMethod.POST)
	public ModelAndView execCommandInUserInput(@ModelAttribute("userInput") @Validated UserInput userInput,
			BindingResult result, ModelAndView mav) {

		ModelAndView res = null;
		String sequenceToBeExecuted = "";
		boolean isErr = false;

		if (!result.hasErrors()) {
			if (!userInput.getSelectedSequence().isEmpty()) {
				sequenceToBeExecuted = userInput.getSelectedSequence();
			} else if (!userInput.getArbitrarySequence().isEmpty()) {
				sequenceToBeExecuted = userInput.getArbitrarySequence();
			} else {
				isErr = true;
			}
		} else {
			isErr = true;
		}

		if (!isErr) {
			if (!roomba.send(sequenceToBeExecuted)) {
				// not implemented yet

			}
			res = new ModelAndView("redirect:/");
		} else {
			log.warn("Validation error occured [" + userInput + "]");
			userInput.setSelectItems(settings.getSequences());
			mav.setViewName("index");
			Iterable<Command> list = repository.findAll();
			mav.addObject("dataTable", list);
			res = mav;
		}
		return res;
	}

	@RequestMapping(value = "/exec", method = RequestMethod.POST)
	public ModelAndView execAllCommands(ModelAndView mov) {
		Iterable<Command> list = repository.findAll();
		List<String> serialSequences = new ArrayList<String>();
		list.forEach(c -> serialSequences.add(c.getSerialSequence()));
		for (String s : serialSequences) {
			if (!roomba.send(s))
				break;
		}
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/exec/{id}", method = RequestMethod.POST)
	public ModelAndView execCommandSpecifiedById(@PathVariable int id) {
		Command c = repository.findById((long) id);
		if (!roomba.send(c.getSerialSequence())) {
			// not implemented yet
		}
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute Command command, @PathVariable int id, ModelAndView mav) {
		mav.setViewName("edit");
		command = repository.findById((long) id);
		mav.addObject("command", command);
		return mav;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView update(@ModelAttribute Command command, ModelAndView mav) {
		log.info("Changing command [" + repository.findById(command.getId()) + "] to [" + command + "]");
		repository.saveAndFlush(command);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView delete(@PathVariable int id) {
		log.info("Deleting command [" + repository.findById((long) id) + "]");
		repository.delete((long) id);
		return new ModelAndView("redirect:/");
	}
}