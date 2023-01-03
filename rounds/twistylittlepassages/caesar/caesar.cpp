#include <bits/stdc++.h>
using namespace std;

#define forc(a, b) for(const auto &a : b)

int main(int argc, char *argv[]) {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);

	int off = atoi(argv[1]);

	string s;
	while (cin >> s) {
		string t;
		forc(c, s) {
			t += (char)(c + off);
		}
		cout << t << endl;
	}
}
