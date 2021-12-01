a = [int(x) for x in open("input.txt").read().splitlines()]
print(len([x for n,x in enumerate(a) if a[n] > a[n-1] and n > 0]))
print(len([x for n,x in enumerate(a) if sum(a[n-2:n+1]) > sum(a[n-3:n]) and 1 < n < (len(a) - 1)]))
