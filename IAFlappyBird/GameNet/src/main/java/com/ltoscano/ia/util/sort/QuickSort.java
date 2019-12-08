package com.ltoscano.ia.util.sort;

import com.ltoscano.ia.genetic.Genome;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ltosc
 */
public class QuickSort
{
    public enum SortType { Ascending, Descending };
    
    private QuickSort() {}

    public static List<Genome> sort(List<Genome> genomeList, SortType sortType) 
    {
        if (genomeList.size() <= 1) 
        {
            return genomeList;
        }

        int middle = (int) Math.ceil((double) genomeList.size() / 2);
        Genome pivotNode = genomeList.get(middle);

        List<Genome> lessList = new LinkedList<>();
        List<Genome> greaterList = new LinkedList<>();

        for (int i = 0; i < genomeList.size(); i++)
        {
            if (genomeList.get(i).getFitness() <= pivotNode.getFitness()) 
            {
                if (i == middle)
                {
                    continue;
                }

                lessList.add(genomeList.get(i));
            } 
            else 
            {
                greaterList.add(genomeList.get(i));
            }
        }

        return concatenate(sort(lessList, sortType), pivotNode, sort(greaterList, sortType), sortType);
    }
    
    private static List<Genome> concatenate(List<Genome> lessList, Genome pivotNode, List<Genome> greaterList, SortType sortType)
    {
        List<Genome> concatList = new LinkedList<>();
        
        if(sortType.equals(SortType.Ascending))
        {
            concatList.addAll(lessList);
            concatList.add(pivotNode);
            concatList.addAll(greaterList);
        }
        else if(sortType.equals(SortType.Descending))
        {
            concatList.addAll(greaterList);
            concatList.add(pivotNode);
            concatList.addAll(lessList);
        }
        
        return concatList;
    }
}