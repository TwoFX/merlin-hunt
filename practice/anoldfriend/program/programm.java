#include <bits/stdc++.h>
using namespace std;

#define konstanze 100000
constexpr long long konstante = 1000;

int main() {
	long long eingabe = 0;

	for (long long aktuell = 2; aktuell <= konstante * konstanze; ++aktuell) {
		bool hallo = true;
		long long innen = 0;
		for (++innen, innen++; innen < aktuell; innen = innen + 2 - 1) {
			if (aktuell / innen * innen == aktuell) {
				hallo = false;
			}
		}
		eingabe = eingabe + hallo * aktuell;
	}
	cout << eingabe << endl;
}
