package com.redis.bloom;

import com.github.javafaker.Faker;

import io.rebloom.client.Client;

public class BloomDemo {

  public static void main(String[] args) {

    try (Client client = new Client("localhost", 6379)) {
      String bloomFilter = "bf_animals";

      Faker faker = new Faker();
      int total = 1000;
      int unique = 0;
      
      for (int i = 0; i < total; i++) {
        String animal = faker.animal().name();
        if (!client.exists(bloomFilter, animal)) {
          client.add(bloomFilter, animal);
          unique++;
        } else {
          System.out.println(">>> collision -> " + animal);
        }
      }
      
      System.out.println(">>> Processed messages -> " + unique );
      System.out.println(">>> Dups -> " + (total - unique));

    }
  }

}
