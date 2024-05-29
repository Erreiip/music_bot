package discord_bot.utils;

public class Couple<T, E> {

    public T first;
    public E second;

    public Couple(T first, E second) {
        this.first = first;
        this.second = second;
    }
}
