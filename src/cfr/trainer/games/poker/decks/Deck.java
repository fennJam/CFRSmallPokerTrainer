// Copyright 2014 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//	
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package cfr.trainer.games.poker.decks;

import cfr.trainer.games.poker.Card;

/**
 * Class representing a single deck of cards, which is shuffled in random order.
 * Cards can be drawn from the deck.
 */
public interface Deck {

	/**
	 * Refreshes the deck such that it is a shuffled deck of 52 cards again.
	 */
	public void resetDeck();

//	/**
//	 * Set a save point for the deck status, can be used for trying multiple
//	 * random draws from a non-complete deck.
//	 */
//	public void setSavePoint();
//
//	/**
//	 * Set the deck back to the status of the last restore point, reshuffling
//	 * the remaining cards.
//	 */
//	public void restoreToSavePoint();

	/**
	 * Pushes and returns the next card from the deck.
	 */
	public Card nextCard();

	public Card peekAtNextCard();
	
	public  Deck unShuffleDeck();
}
