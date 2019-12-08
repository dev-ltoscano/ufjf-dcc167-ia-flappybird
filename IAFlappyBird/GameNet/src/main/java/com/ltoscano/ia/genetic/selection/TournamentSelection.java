package com.ltoscano.ia.genetic.selection;

import com.ltoscano.ia.util.sort.QuickSort;
import com.ltoscano.ia.genetic.Genome;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ltosc
 */
public class TournamentSelection implements INaturalSelection
{
    private final static Random RANDOM_INDEX_GENERATOR = new Random();
    private final int tournamentSize;
    
    public TournamentSelection(int tournamentSize)
    {
        this.tournamentSize = tournamentSize;
    }
    
    @Override
    public Genome selection(List<Genome> genomeList) 
    {
        int maxIndex = genomeList.size();
        int indexGenome;
        
        List<Genome> tournamentList = new ArrayList<>();
        
        while(tournamentList.size() < tournamentSize)
        {
            indexGenome = RANDOM_INDEX_GENERATOR.nextInt(maxIndex);
            tournamentList.add(genomeList.get(indexGenome));
        }
        
        tournamentList = QuickSort.sort(tournamentList, QuickSort.SortType.Descending);
        
        return tournamentList.get(0);
    }   
}
