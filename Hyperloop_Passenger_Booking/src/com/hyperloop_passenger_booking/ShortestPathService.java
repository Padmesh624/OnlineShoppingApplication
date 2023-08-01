package com.hyperloop_passenger_booking;

import java.util.*;

public class ShortestPathService {
    public HashMap<String ,String>  path(String startingStation,HashMap<String,HashMap<String,Integer>> pathMap)
    {
        HashMap<String ,Integer> shortestDistanceMap=new HashMap<>();
        HashMap<String ,String > previousNodeMap=new HashMap<>();
        ArrayList<String> unvisitedList= new ArrayList<>();
        ArrayList<String> visitedList = new ArrayList<>();

        for(Map.Entry<String,HashMap<String,Integer>> map:pathMap.entrySet())
        {
            previousNodeMap.put(map.getKey(),"");
            unvisitedList.add(map.getKey());
            if(map.getKey().equals(startingStation))
            {
                shortestDistanceMap.put(startingStation,0);
                continue;
            }
            shortestDistanceMap.put(map.getKey(),Integer.MAX_VALUE);
        }

        String station=startingStation;
        while(unvisitedList.size()>1 && !visitedList.contains(station))
        {
            HashMap<String,Integer> associatedPath=pathMap.get(station);
            for(Map.Entry <String,Integer> temp : associatedPath.entrySet())
            {
                if(visitedList.contains(temp.getKey())==false && shortestDistanceMap.get(temp.getKey())>(shortestDistanceMap.get(station)+temp.getValue()))
                {
                    shortestDistanceMap.put(temp.getKey(),shortestDistanceMap.get(station)+temp.getValue());
                    previousNodeMap.put(temp.getKey(),station);
                }
            }
            visitedList.add(station);
            unvisitedList.remove(station);
            int minimumValue = Integer.MAX_VALUE;
            for(int i=0;i<unvisitedList.size();i++)
            {
                int value=shortestDistanceMap.get(unvisitedList.get(i));
                if(value<minimumValue)
                {
                    minimumValue=value;
                    station=unvisitedList.get(i);
                }
            }
        }
        return previousNodeMap;
    }

    public void printShortestPath(String startingStation,String[]passenger,HashMap<String ,String>  shortestPathMap)
    {
        String destination= passenger[2];
        ArrayList<String> path= new ArrayList<>();
        path.add(destination);
        for(int i=0;i<shortestPathMap.size();i++) {
            destination = shortestPathMap.get(destination);
            path.add(destination);
            if(destination==null || destination.equals(startingStation)) {
                break;
            }

        }

        if(destination!=null)
        {
            for(int i=path.size()-1;i>=0;i--)
            {
                System.out.print(path.get(i)+" ");
            }
        }
        else {
            System.out.println("There is NO PATH to your destination");
        }
        System.out.println();
    }
    public static void main(String[] args )
    {

    }
}
