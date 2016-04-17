package com.example;

public class JokeSource {
    public static String getJoke(){

        String[] jokeBag = {
                "I hate Russian dolls, they're so full of themselves",
                "People used to laugh at me when I would say \"I want to be a comedian\", well nobody's laughing now.",
                "My friend gave me his Epi-Pen as he was dying. It seemed very important to him that I have it",
                "People say I'm condescending. That means I talk down to people",
                "I was at an ATM and this old lady asked me to help check her balance, so I pushed her over",
                "If a telemarketer calls, give the phone to your 3 year-old and tell her it's Santa",
                "The early bird might get the worm but the second mouse gets the cheese",
                "I started out with nothing, and I still have most of it.",
                "A clean house is the sign of a broken computer",
                "Money talks... but all mine ever says is good-bye",
                "Never laugh at your girlfriends choices... you're one of them",
                "Some of us learn from the mistakes of others, the rest of us have to be the others",
                "I can totally keep secrets. It's the people I tell them to that can't",
                "Take my advice, I'm not using it"
        };

        int random = (int )(Math.random() * jokeBag.length);

        return jokeBag[random];
    }
}
