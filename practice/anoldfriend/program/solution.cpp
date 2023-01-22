#include <bits/stdc++.h>
using namespace std;
using ll = long long;

constexpr ll upper = 100000000;

int main() {
	ll ans = 0;
	vector<bool> notprime(upper + 1);
	for (ll i = 2; i <= upper; ++i) {
		if (!notprime[i]) {
			ans += i;
			for (ll j = i * i; j <= upper; j += i) {
				notprime[j] = true;
			}
		}
	}
	cout << ans << endl;
}
