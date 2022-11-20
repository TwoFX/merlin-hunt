#include <bits/stdc++.h>
using namespace std;

#define pb push_back
#define fora(i, n) for(int i = 0; i < n; i++)
#define forc(i, n) for (const auto &i : n)
#define sz(x) (int)(x).size()

int main() {
	vector<string> code;

	string s;
	while (cin >> s) forc(c, s) {
		if (c == '!') code.pb("██");
		else if (c == '?') code.pb("  ");
	}

	int len = 0;
	for (int i = 0; i < 100; i++) {
		if (i * i == sz(code)) {
			len = i;
		}
	}

	fora(i, len) {
		fora(j, len) {
			cout << code[len * i + j];
		}
		cout << endl;
	}
}
