package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        this.year < other.year -> -1
        this.year > other.year -> 1
        else -> when {
            this.month < other.month -> -1
            this.month > other.month -> 1
            else -> when {
                this.dayOfMonth < other.dayOfMonth -> -1
                this.dayOfMonth > other.dayOfMonth -> 1
                else -> 0
            }
        }
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.inc(): MyDate = this.nextDay()

operator fun MyDate.plus(t: TimeInterval): MyDate = plus(RepeatedTimeInterval(t, 1))

operator fun MyDate.plus(t: RepeatedTimeInterval): MyDate = this.addTimeIntervals(t.ti, t.n)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(i: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, i)

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            private var nowDate = start

            override fun hasNext(): Boolean = nowDate <= endInclusive

            override fun next(): MyDate {
                return nowDate++
            }
        }
    }

    operator fun contains(d: MyDate) = start <= d && d <= endInclusive
}
