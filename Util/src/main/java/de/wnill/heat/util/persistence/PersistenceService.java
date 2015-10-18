package de.wnill.heat.util.persistence;

import java.util.List;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import de.wnill.heat.util.dto.Reading;

public class PersistenceService {

  private static PersistenceService instance;

  private static AmazonDynamoDBClient client =
      new AmazonDynamoDBClient(new ProfileCredentialsProvider());

  private static DynamoDBMapper mapper = null;

  private PersistenceService() {

  }

  /**
   * Access for the singleton.
   * 
   * @return instance
   */
  public static PersistenceService getInstance() {
    if (instance == null) {
      instance = new PersistenceService();
      client.setRegion(Region.getRegion(Regions.EU_CENTRAL_1));
      mapper = new DynamoDBMapper(client);
    }
    return instance;
  }

  public boolean store(Reading data) {
    mapper.save(data);
    return true;
  }

  public Reading loadReading(String id, String timestamp) {
    return mapper.load(Reading.class, id, timestamp);
  }
  
  
}
