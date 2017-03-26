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

package poker;
public class HandOmaha extends Hand
{	
	/**
	 * A hand containing four cards
	 * @param firstCard : the first card
	 * @param secondCard : the second card
	 * @param thirdCard : the third card
	 * @param fourthCard : the fourth card
	 */
	public HandOmaha(Card firstCard, Card secondCard, Card thirdCard, Card fourthCard)
	{
		cards = new Card[4];
		cards[0] = firstCard;
		cards[1] = secondCard;
		cards[2] = thirdCard;
		cards[3] = fourthCard;
	}
}

