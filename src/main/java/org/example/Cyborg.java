package org.example;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Cyborg extends Player {

        public int cyborgMove(Strategy strategy)
        {
            return strategy.move();
        }
}
