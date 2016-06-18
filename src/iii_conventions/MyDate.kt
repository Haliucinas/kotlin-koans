package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate) : Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(time: TimeInterval) = this.addTimeIntervals(time, 1)

operator fun MyDate.plus(time: RepeatedTimeInterval) = this.addTimeIntervals(time.ti, time.n)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(times: Int) = RepeatedTimeInterval(this, times)

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = DateRangeIterator(this)

    operator fun contains(d: MyDate): Boolean {
        return start <= d && d <= endInclusive
    }
}

data class DateRangeIterator(val dateRange: DateRange) : Iterator<MyDate> {

    var current = dateRange.start

    override fun hasNext(): Boolean {
        return current <= dateRange.endInclusive
    }

    override fun next(): MyDate {
        val res = current
        current = current.nextDay()
        return res
    }

}