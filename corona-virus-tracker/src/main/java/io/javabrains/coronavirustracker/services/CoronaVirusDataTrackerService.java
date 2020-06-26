package io.javabrains.coronavirustracker.services;



import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.javabrains.coronavirustracker.models.LocationStatistics;

@Service
public class CoronaVirusDataTrackerService {

	/*Step 1:
	 * This is Service that is going to fetch us the data from the GitHub 
	 *  repo url : 
	 *  ' https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv'
	 *  When the app load is going to make a call to above URL. 
	 */

		private static String VIRUS_DATA_URL =  "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
												
		private List<LocationStatistics> allStats = new ArrayList<>();
		
		@PostConstruct
		@Scheduled(cron = "* * 1 * * *") //schedule to run every day of the calendar
		public void fetchVirusDataGlobal() throws ClientProtocolException, IOException {
			
			List<LocationStatistics> newStats = new ArrayList<>();
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet httpget = new HttpGet(VIRUS_DATA_URL);
			
			CloseableHttpResponse response = httpClient.execute(httpget);
			String bodyAsString = EntityUtils.toString(response.getEntity());
			//System.out.println(bodyAsString);
			StringReader csvBodyReader = new StringReader(bodyAsString);
			
			//Looping through CSV data file
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
			
			for (CSVRecord record : records) {
				LocationStatistics locationStatistic = new LocationStatistics();
				locationStatistic.setState(record.get("Province/State"));
				System.out.println(locationStatistic.getState());
				locationStatistic.setCountry(record.get("Country/Region"));
				System.out.println(locationStatistic.getCountry());
				
				//Setting latest cases and previous day cases
				int latestCases = Integer.parseInt(record.get(record.size() -1));
				int prevDayCases = Integer.parseInt(record.get(record.size() -2));
				locationStatistic.setLatestTotalCases(latestCases);
				locationStatistic.setDiffFromPrevDay(latestCases - prevDayCases );
				newStats.add(locationStatistic);
				//System.out.println(ToStringBuilder.reflectionToString(locationStatistic));
		
				this.setAllStats(newStats);
			}
			
		}// fetchVirusData() 

		public List<LocationStatistics> getAllStats() {
			return allStats;
		}
		@Override 
		public String toString() {
		     return new ToStringBuilder(this).
		       append("allStats", allStats).
		     
		       toString();
		   }

		public void setAllStats(List<LocationStatistics> allStats) {
			this.allStats = allStats;
		}
	}//CoronaVirusDataTrackerService 
		




