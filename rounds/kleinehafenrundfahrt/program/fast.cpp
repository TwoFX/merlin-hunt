#include <bits/stdc++.h>
using namespace std;

#define fora(i, n) for(int i = 0; i < n; i++)
#define imin(a, b) a = min(a, b)

using ll = long long;

constexpr ll n = 22;
constexpr ll inf = 1000000000000;

vector<ll> rands(int amt) {
	vector<ll> ans(amt);
	ll seed = 15;
	fora(i, amt) {
		seed = (1103515245 * seed + 12345) % (1 << 31);
		ans[i] = seed;
	}
	return ans;
}

ll dp[1 << n][n];

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);

	vector<ll> r = rands(1000);

	fora(i, 1 << n) fora(j, n) dp[i][j] = inf;

	dp[1][0] = 0;
	fora(i, 1 << n) fora(j, n) {
		if (dp[i][j] == inf) continue;
		if (__builtin_popcount(i) == 3 && j != 1) continue;
		fora(k, n) {
			if (((i >> k) & 1) != 0) continue;
			imin(dp[i | (1 << k)][k], dp[i][j] + r[n*j+k]);
		}
	}
	ll ans = inf;
	fora(i, n) {
		imin(ans, dp[(1 << n) - 1][i] + r[n * i]);
	}
	cout << ans << endl;
}
