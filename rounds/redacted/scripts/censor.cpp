#include <bits/stdc++.h>
using namespace std;

#define pb push_back
#define mp make_pair
#define eb emplace_back
#define all(a) begin(a), end(a)
#define has(a, b) (a.find(b) != a.end())
#define fora(i, n) for(int i = 0; i < n; i++)
#define forb(i, n) for(int i = 1; i <= n; i++)
#define forc(a, b) for(const auto &a : b)
#define ford(i, n) for(int i = n; i >= 0; i--)
#define maxval(t) numeric_limits<t>::max()
#define minval(t) numeric_limits<t>::min()
#define imin(a, b) a = min(a, b)
#define imax(a, b) a = max(a, b)
#define sz(x) (int)(x).size()
#define pvec(v) copy(all(v), ostream_iterator<decltype(v)::value_type>(cout, " "))

#define dbgs(x) #x << " = " << x
#define dbgs2(x, y) dbgs(x) << ", " << dbgs(y)
#define dbgs3(x, y, z) dbgs2(x, y) << ", " << dbgs(z)
#define dbgs4(w, x, y, z) dbgs3(w, x, y) << ", " << dbgs(z)

using ll = long long;
using ld = long double;

int main(int argc, char **argv) {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);

	string s;
	cin >> s;
	int w, h, m;
	cin >> w >> h >> m;

	int y1 = atoi(argv[1]);
	int x1 = atoi(argv[2]);
	int y2 = atoi(argv[3]) + y1;
	int x2 = atoi(argv[4]) + x1;

	cout << s << " " << w << " " << h << " " << m << endl;
	fora(i, h) {
		fora(j, w) fora(k, 3) {
			string p;
			cin >> p;
			if (i >= x1 && i <= x2 && j >= y1 && j <= y2) {
				fora(q, sz(p)) cout << 0;
				cout << " ";
			} else {
				cout << p << " ";
			}
		}
		cout << endl;
	}
}
