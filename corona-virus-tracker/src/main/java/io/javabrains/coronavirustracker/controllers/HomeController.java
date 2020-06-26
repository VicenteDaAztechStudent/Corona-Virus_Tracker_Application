package io.javabrains.coronavirustracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.javabrains.coronavirustracker.models.LocationStatistics;
import io.javabrains.coronavirustracker.services.CoronaVirusDataTrackerService;

/*Step 3:
 *Building a Controller
 *
 *This is what get accessed when loading home URL 
 *not Rest Controller cause that would give a JSON response.
 *Want it to render UI , we need a HTML response.
 *
 */


@Controller
public class HomeController {
	
	@Autowired
	CoronaVirusDataTrackerService coronaVirusDataTrackerService; 

	//url = 'http://localhost:8080/'
	@GetMapping("/")
	public String home(Model model) {
		
		//Sums up all the cases reported globally 
		List<LocationStatistics> allStats = coronaVirusDataTrackerService.getAllStats();
		int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
		
		//Creating Model Attribute objects, will be needed in'home.hmtl' file to get state, country, and lastTotalCase
		// and render info in HTML through ThymeLeaf
		model.addAttribute("locationStatistics", allStats); 
		model.addAttribute("totalReportedCases", totalReportedCases); 
		model.addAttribute("locationStatistics", allStats); 
		model.addAttribute("totalNewCases", totalNewCases); 


		
		return "home";
	}//String home(Model model)
	
	//url = 'http://localhost:8080/united_states'
		@GetMapping("/united_states")
		public String us(Model model) {
			
			//Sums up all the cases reported globally 
			List<LocationStatistics> usAllStats = coronaVirusDataTrackerService.getAllStats();
			int usTotalReportedCases = usAllStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
			int usTotalNewCases = usAllStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
			
			//Creating Model Attribute objects, will be needed in'home.hmtl' file to get state, country, and lastTotalCase
			// and render info in HTML through ThymeLeaf
			model.addAttribute("locationStatistics", usAllStats); 
			model.addAttribute("totalReportedCases", usTotalReportedCases); 
			model.addAttribute("locationStatistics", usAllStats); 
			model.addAttribute("totalNewCases", usTotalNewCases); 


			
			return "us";
		}//String home(Model model)
		
	

}
