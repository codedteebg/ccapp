package com.babbangona.barcodescannerproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.babbangona.barcodescannerproject.model.msaT;
import com.babbangona.bg_face.LuxandActivity;
import com.babbangona.bg_face.LuxandAuthActivity;
import com.babbangona.bg_face.LuxandInfo;

import java.util.ArrayList;
import java.util.List;

public class SelectMSA extends AppCompatActivity {
    private List<msaT> msaTList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MsaAdapter msaAdapter;
    private SharedPreferences myPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_msa);

        myPref = getSharedPreferences("User_prefs", 0);

        recyclerView = findViewById(R.id.msaRecyclerview);
        msaAdapter = new MsaAdapter(msaTList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(msaAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                msaT msaT = msaTList.get(position);
                Message.message(getApplicationContext(), msaT.getStaff_id() + "is selected");
                CallLuxandPrefs(msaT.getStaff_id(), msaT.getFacetemplate());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareMsaData();
    }

    private void prepareMsaData() {
        msaT msa = new msaT("T-1000000000001", "Tobi Adesanya", "RlNESwYAAAADAAAAAAAAAAMAAAAAAAAAAQAAAAEAAAATAAAAVC0xMDAwMDAwMDAwMDAwNTE1AAEA\n" +
                "AAABAAAAEAQAAGZzZGsDAAAAEAQAAD6B6kL0PwQ9nJWFPR6s0r0hlnG9+Z9kvOdbpbxOPKy9QV7w\n" +
                "vYvkXb2LmZS9vo/eu9c7bT1m0Tw98kfkPXCniD1bM1q9rekivaaEaD3QX2+9wgUSvTlMVzz81oa7\n" +
                "9rXFOpu0lz1vyBU+vytaPdusxL1998y9bn98PQSI0L0IA9q9FMyPPYQSGD1AtLG94WoZPZgP2D3E\n" +
                "wPO8EKPNvQfzHz2tYqy92Ri4PEO2hbzflkm93BfAveDIMTxO2Rw9sF+mO3NHPrtxeKe9CsyIvXHd\n" +
                "uDzoHdM9fpUyvf3LwL0Lina8rJekvJrESbysD0E84YGwvFkbRT3ilNE9huTyPS/zED1Tq0y7GF5K\n" +
                "PHWEp70Lmfq9VkkPPcn3oD2l1p89XHGWPWHcLz13PcC8hIzuupoSxLz1bTW9lyWtu/vRrbz8x8g8\n" +
                "RdrCvTFKvz0a2H+9oFqCvYUjrb3CuB0+JyqWvcPmdL3TwRq9oFZkvYQVob0+yeC9mQDHPMzMyjwJ\n" +
                "dem9lF3/uzDlNb2od669uUAEPOw2ED1MxpQ9l3X4PfuLpL3xD989yEbEPfUOQ72brii905KNu7ou\n" +
                "cLwDjbG8HLf2uwk6oz0gY/g90/J9vHufdT1TzJC7icQIvXQnvL2wtNI8HBA5PH7oKL3ln7O81ZRw\n" +
                "Oimsc73CSgM+BUNPvefDxjx72oC8NrFIPAoJpT2Ixm89tuKfvd2OijykUMg9keQ9u8OeTz0VwvW8\n" +
                "to3avBtjUT3UJqw9z34qvHndkT1jTkC8TkhBPTNlEb3cEOI9uHFYveKyJz1g+Yk9TLdHvdTy2j00\n" +
                "tXS9Rx8BvILa3r3L44q95gQGPYVGo7yZzKw7TXb8vGnS27uXfwA+WZk4PdyQKL2Lrbc9jGxiOy1+\n" +
                "zTtkCSc9vql1vUYb8b1aDjS9EarzPULwvr2VVEI7TQ0gPChVEr1pszu9DSfOPKrblr1xdPK9tXfh\n" +
                "PHdeEj1H7ec9i8WYvDHbLr1OZo87oGKwvS2Cfry0Te88Bn9BPJRqarviBsk8oSkrO6o9hj37Ioo9\n" +
                "oHA4uo7OaTwcZlO9zhkPvP4lUDwz0AM9BT4OPefzq7yaC369AfRWvZBrBT21hBW9/GrGvZxeST3H\n" +
                "kUE8cTp7PRBH272z7qC9byihvTkREbybmro97O+VtxvzfT3CzsM8xb9SvQhN6Tzo9Iy8qXoAPWqH\n" +
                "lju54C0+/ZWIPVq1qb2CWbi9SlDfPGkg6LxF+rC9nYaCPJCucDwYOsu8z/bZvHpItT2T5+C8F4YI\n" +
                "vU/GvD0Ua3a9hNafPeHgFTtK7E69Zo6OvJfP4zz31dU98Qj8vDJ9oT3s2JC8YLYXvM0Uubzmbqi9\n" +
                "EifSPIfkfj01WIo93JivvWjCaT0c1EO9AgAAAAAAAAACAAAAAAAAAAEAAAAAAAAAAGAAAABgAAAA\n" +
                "ACQAAP7+/v7+/v7+/v7+/v7+/v7+/tSElJTE8Pfv9OXX09LQzMTGztTj6O7u9fHp6eHc39HY39rV\n" +
                "0NHR0NHPysjDvrq3vL7GysnIwbuaXV9SZHic+f7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v7+9aWWqpTE9/f08+jW09LRyL2+xNHX4ePo6u3p5uPa19PU2dfXz8zNzc3LycfBvLa2urzAx8vN\n" +
                "xrylhm1mY22F5v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+2pmcsqzf+vz48+rVz9HO\n" +
                "w7u7wcjP193g497Z29nb2dXW2tfSycHCxcLAu7ixrq2stbu/wsfNysS0mX2Ec3CG0v7+/v7+/v7+\n" +
                "/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v78sqi5uc/V9fn07ufPzc7Eu7Oytrq7wMTFzszKyMTDxMXL\n" +
                "zsjDvbm0sayrqqyqqaakqK65xsfJzcy5n5+Wh317vP7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/v7vl6rCw8bQ6fbw5uPRxce6sK2ytbrCxc/W297TzcTCvL/AwcLDvr6/v8XFw8G+t7Szsa+1\n" +
                "vMDDz9nJp6efno+Co/z+/v7+/v7+/v7+/v7+/v3+/v7+/v7+/v7+/v7+/v7pqcXMxtTe7e/t6NzI\n" +
                "xsW7tbW6xc3Z3uHj6Ojq4N3V09jd297U0dHPzc7GwL+7tK2usbO1uLq+0eLHqqWtrqCcm/n+/v7+\n" +
                "/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/vu7wszQ2d/k6ejq4dTKysK0ra6wub/I09bW3ODh4NnU\n" +
                "1tjc4t/R0cvIxr+3sKuppKGmrLCvuby+0eLGuK2yprCkm/b+/v7+/v7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/v7+/v7Yzc7U4ePl5Ojk2M7GwbivqKits7nAxMjS0dLZ1MrHydDX19LRycC1qaikoqaqpKCe\n" +
                "pqmrsLvC2dravrG4uritlOn+/v7+/v7+/v7+/v7+/v7w+/7+/v7+/v7+/v7+/vy5xdfW29rk4+ff\n" +
                "2s+/uLKxr7C5xtXR0c3S1NbY0czJy8/c5OHYysG7uLfCyMjFv7azraakqrK9zuzex77Bxb3FlOb+\n" +
                "/v7+/v7+/v7+/v7+/v7q0O3+/v7+/v7+/v7+/visy9HO3uTn5OPb3M6+t7K0uLi/ydzi39jT0dTX\n" +
                "2NTN0Nfe39jOyMjLzdHUy8jGvrm6wL+6tbW4yObr5NPV09XZpeb+/v7+/v7+/v7+/v7+/v787LHO\n" +
                "+f7+/v7+/v7+/vS1yuDZ5uvn5t/Y18i+vcjOz83Lz93f29jS0NTQ0NDPzdLc3NTOzs/O09bRy8fG\n" +
                "x8PGy9HTzsfBxuHj6d3a3tTOo+X+/v7+/v7+/v7+/v7+/v7+/PHUttf5/v7+/v7+/vS11eTq7+/m\n" +
                "393c29XU3ePj4OHe3erp49rR087MycvLzM3S0s3Ky8vR1NfZ0dLV1NXX19ra2NjU0uDw3Nzf3tHL\n" +
                "s9/7/v7+/v7+/v7+/v7+/v7+/v7w6suwzvX+/v7+/vW84eLl6+nk3ePg5d/a3dvd1tTU2N/d1NDK\n" +
                "x8nJx8vHxczPy8TCw8fS0tXVz8zJxMnM0s/NzNDa4d/u4+Lu4c/Msd/7/P7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/vrtyqzL9P7+/vu73ePp6Obj5ePq48zBw8fJwr6/wcbHysrCxMTCxsrEw8rNxcG/xMzPzcrG\n" +
                "wLqysra5ube4u7/A0uHx6+Dr5NTCrt3+/fz+/v7+/v7+/v7+/v7+/v7+/v7++vDQr8by/vzI4enr\n" +
                "6Obl5ubmw6Ocn56jq6mrr7K0uby/vr+9wcTDxMrGvbm8wsnFwr+6taunoaSimJKTlJ2ovtjz9u7m\n" +
                "49nMsuL+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v778NCuw+7M4uvq5+bn6OPYspeOiX2BjZWPm6Sp\n" +
                "ra+1ury7ur69v8TAubq/w8G+t7KvopeTgoaHgXt2f5myv8jf+fby59rUue7+/v78/P3+/v7+/v7+\n" +
                "/v7+/v7+/v7+/v7+/vzy0Ky55ezn5+jp39O8oY5/fHV0eIB/h5igpKeutLO0sre6uby6tLa5vby2\n" +
                "sKWelYWDe317dXF8k7C8v8jO7Pr36uHZvfT+/v7++/3+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/PLW\n" +
                "6O3l5uvl0cGso5qUjIF8e3+Bho2TmZ2lp6iprLG0tri1sq6xtLSupZ+Wi4OJg357fYOLma2vrbK7\n" +
                "1fr67eTdwfn+/v7+/vv+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7s6uvl7e3bwbGmn5yak4+IhIOI\n" +
                "jI6PlJqgoqKlpquws7SvrKanqKekmpSNhYaBenp6fISIkpicmay6xOL99OfizPz+/v7+/f3+/v7+\n" +
                "/v7+/v7+/v7+/v7+/v7+/v7+/v757ejp8+bDrqiXmp2UjomHgoOEhomPk5aanKCipKSmp6anpZyd\n" +
                "mpyXj4eCfXl3dHl+g4mMjpGTmKevuMXn7d7h2v7+/v7+9fL+/v7+/v7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v798Obt6tO1oJmcnZmSjY2Jgn97fYCKkJOWnJycoJyfnaCgn5qVkJCKhIB5dXR3e4iNlZeXmJeV\n" +
                "lpiboK3F4d/i8P7+/v7+/vz+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+7+bq28Kvn56gnJeWl5WT\n" +
                "ioR7e3d6f4iUm5eWmpibkpudnJKLiYaBeXBvcnd9jZagp6qspJ2bmpucnqW5ztre9v7+/v7+/v7+\n" +
                "/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+6+LbxrCnoJ6gop6Zm5qYj4iAe3Z1dnyKkpGWmpmcmpya\n" +
                "nJSOhoB4bGtudX+Ml6Kqqqqrq6WinpiamZ6rvc3V9f7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/v7+6d7Pu62moqGinZKMjIqIhIB6dnR0d3d+jpSYmp2coKOcmJGKhXtzaGtze4SQlpaTj46I\n" +
                "h4+Yn52XlJejsMLS7v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v785tbGtK2lo5+LeXN5\n" +
                "iIqIfXFwbWtwcnJ7iJOYnaOmqaefmpaOhnluZ251e36Af42an6iol4V7doqUkpKcprnO2+j+/v7+\n" +
                "/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7w4My7saiml3+Ei5Gan52YkIFvZGdqbm94h5agpauu\n" +
                "r6qfnpqRhnhqZm5zc3B4iJmgpKyoo6OnrYaCkJCVoq/F2M78/v7+/v7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/v7+/v3t28e6sKykkbq7l5OMlIJ/f3p0amVma25zf5KfqbKzs62oqaacinRlaW1saW5+iYqH\n" +
                "jIV1eJGhr7KPiI+Voqy+18X5/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7u3MS4r6udprJ0\n" +
                "WVZFNkAzN0FZaWdma3B1epChqbW2tra2sa6iknFpbG1ubW5vXkguPS0qME5SZ4WIh4+SoKi71dr9\n" +
                "/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7y2sC1q6qdb0lfm7hpPklOLCtRT1dkcYCEd5Cj\n" +
                "qra5vby8trKmmnp0fHpzbVhVbjkrXDIxRZ+QZ0NTeIiToae30PP+/v7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/v7+/v7+/v7v2sCzrK6mmmeJ2OKKUSYvLyucfFNReJiWfpGjrbK3vby7uLOqnH2FlpVuWWyQ\n" +
                "v0QmJzI2S7m8f3mTkZeYo6i0y9/+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7u28O3tLjA\n" +
                "0756kMmyUCQkJ02lh5J+e6OWg5ejrrG5wL+7tbCpn42MnJZnb5aWuH8oIiMsf7OKgKKsr62oq6y1\n" +
                "yML+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7s28q/w8zPzsCdhn+PaFJSVXZ4cnGBkI+K\n" +
                "iZqnsszz/PTpzLeroZaIjo2Uh3Z4eopsREFhkYiGkaO5wb+8trG1x9H+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/v7+/v7+/v7+/v7s3dPO0NPUybqmmI+EgIyPkomAe4SOlY2GkZ2wy+by+/Xp3tC7ppuQipSe\n" +
                "lomDgH5+eXN4c3+Jk6i5wMPCwrq5yd/+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7s49/f\n" +
                "2tXPxLqqm5KGg357goWOkJWVj4uQmqjG1t/r9/Pl3NfNs6WakpKcoJyTiYF0b3F1fIWVpbe+wMTE\n" +
                "x8K/yd/+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7s5ufr59bJv7mvqKKcmZmcn5+fn5mU\n" +
                "k5ieqcPW19zk9fHk3NTQwrOmnpuanaKem5uWlJaXm6KstrvBwsXJz8zFzd/+/v7+/v7+/v7+/v7+\n" +
                "/v7+/v7+/v7+/v7+/v7+/v7t6O/x7d7Lwbq2sa2sq6moqaelopudoaOouc/W3Nvn9/Xl29jQzMO3\n" +
                "rqmpp6enpaampKeqr7O2wMLExsjM0tDJ0OP+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7u\n" +
                "6u7q6uTWxLy7uLWzs7KvrammpaipqKq2zNXV1dnr/PPk2tfS0cjAuLW3t7Kuraqurq+1uby/xMbH\n" +
                "xsjKzdHPzuX+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7u6Obl4NvXzcK9vLq1tLSyrKqr\n" +
                "rq+rq7fG1djX1dvo8+7k3dfS0tDIwr68u7i7t7Oxs7G1uLrAxMfEwcPFyM3Q0uH+/v7+/v7+/v7+\n" +
                "/v7+/v7+/v7+/v7+/v7+/v7+/v3s4tvc2NPLy8W9urm3tbKuqq+xt6+qscTX3tzd2Nvp8O/j2trS\n" +
                "0dDKwb+7trW8vr64t7S0t7e9wMC+vL2+wsnLz9/+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v3p3tjR0MnFxcK/uri0srCwtri3taurtsjd5ujj4eDo8Ozk3NvX1NDLwL66trK2vL++u7u6uLm7\n" +
                "urm2tLi3usLH0N/+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v3l2dPQzcK9vr6+u7m2tLS5\n" +
                "vr23r6evwNPm7O/p4+Pt8/Dn3trX1tPMwLW3sa6xtru9vLy6urq5s62srq+vsrnAydr+/v7+/v7+\n" +
                "/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7k1tDJxry1t7u8vru5uLi9vLmzraqtxuTq8PDp5uPs8fHn\n" +
                "3drZ19XRyK2kqKmtsrS6u727vLevqqilp6iop7C8xt3+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/v7i08vFwrq2tLa7wMDBwb+9urWvrKaj3e/v8u7m4+Ll6OXg2dXX3dXPzLGVnKSnrq+ztLi5\n" +
                "u7m1rKilo56ipK+2w97+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7l0MbAvLi3tbq9wMTD\n" +
                "w8O9u7OrqJ+v6+7v8u3j3t3g4+He2dTX3NTPz8KUkpyfp6uwtLe5u7e2sKmnpKGen6exwOH+/v7+\n" +
                "/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7pz8O9u7y6ur7Bw8K/wMC+trGqopbO8e/s6ePf3d7e\n" +
                "4NzZ1dHV2NLT0s+eh5edpKywtLa1tbKysK6opqOdnqevwOT+/v7+/v7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/v7+/v7wzsW+vrm4ubm7u725ury8t6ymm6Hs9u7k4N7c1tfd3dbQzczR09XW1tW8h5Seoamt\n" +
                "rrCsrq+sqaaloqGen6Svv+f+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v73zcO+uLS0tLOy\n" +
                "s7CxtLe4saagl8D29OXa1tXPy8rL0czFwMLHycvW2dnQlI6boKWoqqmppqaop6CenJ2en6Ksu+7+\n" +
                "/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v760MG7ubSyrautsLOtr66tpqKcltnw59vPysbC\n" +
                "ubO5u7e1s7a4vcDI0trUuI2aoaOjpKSioqSin52cm5iam56rvvP+/v7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/v7+/v7+/v792MK6ubOuqaWnqKmqqqWhnJuZm9rh2dPLwbevqqWop6CeoaKkrLnDzdHVx5SW\n" +
                "nJ2jpJ6dnZ+dmpmal5WYm52svvn+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+3sS7uLOs\n" +
                "p6OipKSmop+empuZnM/Qy8G4qp2Wm5+gnJSLjoiJk6i1vcHHvZuXmp2en5aTlZeTl5WTk5KXmZ2t\n" +
                "wvz+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+5sW5tbKrop+fnZ6cmZmZmZmVoMK8sJiG\n" +
                "d259jJGSiISAfHVrY3eNmaWsrZeYnJ2fnZ2ek5OVk5OPkJKXmZ+uzP7+/v7+/v7+/v7+/f3+/v7+\n" +
                "/v7+/v7+/v7+/v7+/v7+7Me5tbGqop+enJyWlJKTlpqZlqiXcUcwKStDcIV+eHZ1b1QsGx0zQmOE\n" +
                "lY2XnpqXlpaTkI6NkZCPkpGVmaGq0Pf8/v7+/v7+/v7+8/L+/v7+/v7+/v7+/v7+/v7+/v7++Mq6\n" +
                "tLGqo6CdnJqVlZaXm5+enJR9WkxUXlphZGd2cXBqUUZEQENLT1ZthouXn5mZlJCRj4uIjImLkJSW\n" +
                "mKGv5/329Pv+/v7+/v7+/f3+/v7+/v7+/v7+/v7+/v7+/v7+/dG8srCrpqCdm5mXlpSbnqGhoZqD\n" +
                "c2Zyd3l6eHJlZmJSTlVka2ZcWFxwhY+coZ2XkI+QjoiHioaIjpKVlqCx9f7+/fXx+v7+/v7+/v78\n" +
                "/f7+/v7+/v7+/v7+/v7+/v7+/t6/tbCppqOgmpiZlpidnqivq6KSjoiDfn6EhIV6cXh+g4GDgXt1\n" +
                "dXOFkpigoJuXkpCNjImFhIWDjZKSlaO8/P7+/v797uT0/Prz+/3r7f7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/u7CuLCqpaKgnZmamZugqLi4trCWkoyFipGQk4+DgI2UkY2Ni4uLiomVm56kpJ2XkoqLh4WFhYSE\n" +
                "jZGRk6PO/v7++/z6+fTc1uzv1e3o7P7+/v7+/v7+/v7+/v7+/v7+/vnIurGqpaSfnJybmKCpuLy9\n" +
                "vLyflZKQlZWUkpGQjZGUlJCQkI2Ni5KbnZ+kpaOak5CLi4mEhoiKjI+QlKPh/Pr59/n4+Pf38t3V\n" +
                "0N7X1/7+/v7+/v7+/v7+/v7+/v7+/v7RvbCtpKOioJ2ZnaewvLy8vbuhmZKNkI2NjZeTlpWYm5GU\n" +
                "kpSXmJ2foKGjo6SgmJKMioqGiImKjI2Sl67w+vn49/b2+fb29vXIusbEvP7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/v7jvLOrpqOlo6CipKy2uLm7vLmkmo+DgYuNj5GPk5KaoqCemZecnKCioaKjo6KinpaQjoyK\n" +
                "iYuMjY6SnsL19/b19vT0+Pb29vTUrqyerP7+/v7+/v7+/v7+/v7+/v7+/v7zw7auqaampqWnqLK1\n" +
                "t7e6ubGhloyIho6OiYmKjo+Ump2ak5WUmZ6ioaCioaCgmZiSjYyLiYqJio6UpNf39vb19vX0+Pb0\n" +
                "9fTbpJ+Xrf7+/v7+/v7+/v7+/v7+/v7+/v78zbiwqqelp6eqrLSys7SvrKmako6RlpmdlomIjYON\n" +
                "lJ2bl5ONkpedoaGgn5yfnZWTjomJhoeIioyWrOr39vX08/X1+Pf09fTem5mNpv7+/v7+/v7+/v7+\n" +
                "/v7+/v7+/v7+4LywraqnqaqrsrSrqKihn6KSmJ+gn5Ser6CBeICWpKKgnpyVkpCTmZeYm5manJiV\n" +
                "kYyJhYWEh4uavPT29vXz8fPz9vX19PDqp5iEmv7+/v7+/v7+/v7+/v7+/v7+/v7+88Gyrquoqqut\n" +
                "sKujnp2bpKqnpZ6Mi42OoKuYkpymlouCiJmhm5KSlY6Tlpaam5eWk46Kg4GAhI6Z0/T09fT08fHx\n" +
                "9PT09PDxuZmIj/7+/v7+/v7+/v7+/v7+/v7+/v7+/cy3r6yrrK6vr56RkI+Yp6+vmIiGjI+Qkp2h\n" +
                "op6UkoyDfHmGn6Ogl46Qi5WXlpaTkYyGgYGAg4+f7fX09PP08vDv8fTy8/PxzLmPgP7+/v7+/v7+\n" +
                "/v7+/v7+/v7+/v7+/uO8ra2trq+tpZOKeYWRmp6OgISKjpCQjY+YmpKNjoqLg3xxeZmhmo+NhomR\n" +
                "k5KRj4mDfXp/gI249fb18/Pz8vLw7/Lu8vPxudbVf/7+/v7+/v7+/v7+/v7+/v7+/v7+/vi7p6yu\n" +
                "sa+spJJ9a2pucG9kcYCIhYOAeHmBg4B7gISBeXNrYmWBi4SEhH+Gk5GQjIWBend3fYze9vb09PT0\n" +
                "8/Py7+/v8/Lz3NPkhv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7Xsaqusq6ooIh0Xk5AQk5SX211cm9u\n" +
                "a3F2dW9rbnFsa2hWREVSXV9pe32Eio6Oi4V7dnJ0fKD19fb09fT08/T07fDx8PDv6svsmP7+/v7+\n" +
                "/v7+/v7+/v7+/v7+/v7+/v71t6uss7CpnIRxZ3BeU21wZl1ZUVdRVV9rbF5dWllRV1FDPDo1MTRF\n" +
                "Ynd/hoiIhH52bWtteNT29Pb09PX19PTz7vDw7+7s7LzvsP7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+\n" +
                "1K6rsq+omYV1cH95d4SSioFvVDgqMktMSlhRRTw+RUVUXVdLO0RJUmdveoSAfHZqZ2ZomPX29fb2\n" +
                "9fX09PTz8vHw7e7q67Tvxv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+9rKmsLKlmIp7gIuEgoiUmJaT\n" +
                "e19LP0Y9Mk48MEFOco2amI97dHhuXWFsdX15dGxhY2Nn2vf29vb29fX09PTy8/Lw8O7r7bzn2/7+\n" +
                "/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tymqLKomouEipORkJKWlZGZmYtvYVJDQ1hdbXiVpJ+hl5GN\n" +
                "hH9rZGRrcXNvamNWWV2J5fX29vb29fb18/Tz8/Lx8O7s7sfZ5v7+/v7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/vu7o66qnZCKi5OUk5eZmJWXoKaQem9nbXuFmaedmpGUjouJgHhvZmFla2pmYVdQU2lRSXO1\n" +
                "6ff39fX18/T09PLy7+7t7tPI5/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7soqepnpOOiYyPi4qL\n" +
                "l5GSppihlZaRpKesrKahloZ+gH16b2twa2NgZGNeV1BPU30vHypCY4igr8Xc6vP18/Hx7+7t69+U\n" +
                "sv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v3OmKCpnpaPiYeLi4OEiouLi46Vm52gq6+rppeahX10\n" +
                "cG5lYmlwamRiXVlVUElKaHgaERknOElNRUJNXGGO1rycm9Pu6uehnv7+/v7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/v7+8IUxcZyioJSLhYaBfX98goeEf4SNiomKkI+KiIF/eG9rY1NRX2hqZl5aWFRPS0RVcm0U\n" +
                "DhIXIC0zNTo8Q0M7NjtATWC26Oix5f7+/v7+/v7+/v7+/v7+/v7+/v7+/v7UUysjYpqapJaHgH16\n" +
                "dnd3eICBgoSEfnl5dnV1dnlpZmBVS0VNWlxeV1ZWVE5NSEtcbmMPDxASFx0mKy80Njo2KCE+R1Rh\n" +
                "xuq03P7+/v7+/v7+/v7+/v7+/v7+/v7+/so8LCMdTZWSoZiMf3l1dHFtanh4d3p8eXV1cXB3c2hY\n" +
                "WUlCQUlRVFhWVFBTUEhHSE9fcWEGCA0QEhcdIygsMC4hHiRAQ1FVdeTC0v7+/v7+/v7+/v7+/v7+\n" +
                "/v7+/v7+zEErJB0ZPpWLl52Og3l1b2pkZWRnZ2lpZmVnZ2poX1lNRD8+QklKS1NTTU5ST0VERlBl\n" +
                "c1gFCQoMFBYcHiQqJRwUEjRCQUVKWKvQw/7+/v7+/v7+/v7+/v7+/v7+/v7mVDAgGhYVNpCHhJqP\n" +
                "iH93cG9mZF1YVVVTV1VYWVtUTkc/PkBERklGRkdLUFFRST88RVhmclwGBAcKEBQXHCAiIREKKD4/\n" +
                "PUFCUWvVt/7+/v7+/v7+/v7+/v7+/v7+/vh3Py4XEgwNNI2EeYyUi4Z9dnBoZ15aUklFQkI9Oz5B\n" +
                "QT5AQ0dGSEtJSE1UVFJOQTo+SVxmbW4ZAwQJDRMUGB0fGQgaNTk4Njs/PkjLrv7+/v7+/v7+/v7+\n" +
                "/v7+/v7+/q9EOycUDgsILYqEfHuQkYqFfHRwa2ZjXFZRTEdDQ0RFQ0RGSkxPUU9LTFNaW1NDNjtE\n" +
                "VmFlcXcmBgcGCw8TFxwhHxkxMi8xMjIzMTjFrf7+/v7+/v7+/v7+/v7+/v7+41I/OiUTDQkFI4KF\n" +
                "e3R0jomHg3t3cW5sbWlmX1lYWFlWVFNNT1JWVldYXFpdUkc2Nj5KV2BlbXYsGxoJCg4SISonLi8o\n" +
                "LC0wMC0oHivUqv7+/v7+/v7+/v7+/v7+8sadbEU8MSgSCgcHKXyAfHR1h4uFhYR9dnV0dHRub21s\n" +
                "Z2VnZGNeXFxcXF9dZWNXOTIzOEJSXWBmbm0nIxURCBAiIh0nJScqKisuKR8cJETeuf7+/v7+/v7+\n" +
                "/v7+/vWqXlRwTkA4MyIOBgQGJ3Z+fnpzcoaFg4WEfnx5fHt2cXNub29xb21uaWpnYmJjY15OLSs2\n" +
                "PEtZX2FmaGQrNBwiICYkJiMhIyEiIykqHhkhOUpcbf7+/v7+/v7+/v7+43JMSmJiRDg2Lh4KAwQH\n" +
                "J3B5fXx3Ym2AgYSFgoB9f4F6dnFwbmxtcG9ub29qZ2NkX1IwJi85Q1NcXl1gX1QjPiY3NjEsHhsb\n" +
                "Hh8iICUeGB0wN0NCQ/7+/v7+/v7+/v7hYU9GSGBMQDEwJxsLAwMJJGt3d3p1Y0Npc3iBgH99fn99\n" +
                "d3Fyc3FscGxpa2tlYmFZSjgmMzY8TllgX15cW0AfPiwlIRwZFxgZHR4iIxsOGSw3ODpDRv7+/v7+\n" +
                "/v356NBkTEI+R19EPzMtKBkJAwQPJWR2eHZ0ZVBQWWp6e3t6eHhvaWdrbGlqY2NkZWNhXltIMSUu\n" +
                "ODtHVF5iXFlVTSwbMyIcFxUUFRYVGh8hGxERIi4yMzc5O/7+/vnksXlPO1pGOjc7S1NDOjAuIRcG\n" +
                "AQoXKV5yeHR0bGNYR1pubnJzcmpcUVFZY2RjYF5eXFlSTj0pIy0zOUdVXF5dV1NPRR8iJRYRExMT\n" +
                "FBYXGR4bDg4dKS80MTQ9Qf3jn1YtJygpSVI/NzU6S0tEOS8rIhkGAhAZLF9rcnV3eGtnUj1UXV5i\n" +
                "X1tUQkBOXFxgW1VTTz9AOCIdLjU5QlVbWlpUV1BCMxYcFxEPEhQUFBYWGRkSChUkKCwxMjA4PXk3\n" +
                "IiI7VlpcXUU8NDQ3Rk1ENS4rIBQFAxcaMFtsbW12dXJsZFNQQ0JDQlFHODk9RVFQR0g/PTYqGRQb\n" +
                "MDhAS1VWWFJRTUQ4KBQTExAQEhIRFRUXFRMGDxYbIyUpLi80OTEoL1JeTkZLTEI2MzE2RUY5MS0m\n" +
                "HhADBiIPMlpmaGpzdHRyZ2RXTzgeISowMDExPT46LzEoIRwZGBsnNjxERk5VU1BPSj82IBIQDxAP\n" +
                "EA8RExcXEgYIERcZICAnJC4yMlRdW1FFQT9MRzk1MTM1QkE4Mi0mHg4EDSMNNVVjZmxsbXBxbGVg\n" +
                "X1EvHBYYHh4fIB8YGRUUFh8oKy4yOD5GSExSU1FMRzwxFw8RDw4QEBETFxYUCgQJDxYXGyAkJyot\n" +
                "K1hRTUE/Pj5LRTU3MTA3QD85Ni0nGQwEFhoLNFJkZ2pobG5saWlpaWJPTDUrKCckGxgSFBUUHykz\n" +
                "NDY5P0RJSVJWVE9JQDgnEhEQDAwPERESFRYPBAQJDhQZGB0jKCsrJ09JRD0+OjpIOzQwMC8yPDkz\n" +
                "MColGwgEJh0LMk5ma2ptaGltbm5sbmxsZlJKQT02MC0kJickLjY7OTg+SlBSVFRVU0tEQTUXEA0O\n" +
                "Dg0ODxITFhIJAgUFCxAYFxsfJSonHEVBQD08PDlIOjMwMCw1PTwzLykjHAoKHhAILU1hbGtubmpx\n" +
                "dHNxcnNxc2hgXldWUUpFREY8PDk7PENQWV1ZWFNTT0xJPyMQDg4NDA0OEBETFAwHBQUHCg4UGRsg\n" +
                "JCEfED9BOzk6OT9AODAxLS45OjUzLikkGAgOJwwIMExdam5wcnJ2dnRycW5tb2tmYF1aVlNNSktH\n" +
                "R0VHTVVbXl1bWFdYUk5IORYQCwoNCwwQEBETEgkGBAUHCRAXHBwfIR4TDB4AAAAdAAAAQQAAABwA\n" +
                "AAAuAAAAMAAAACQAAABCAAAAOgAAAEEAAAAXAAAARAAAAEoAAABFAAAAHgAAAEsAAABDAAAATAAA\n" +
                "ACYAAABRAAAAOgAAAFEAAAAvAAAAUwAAABMAAAAVAAAAIwAAABEAAAA6AAAAEwAAAE0AAAAUAAAA\n" +
                "GwAAAA8AAABEAAAADgAAABYAAAARAAAAHgAAAA8AAAA/AAAADwAAAEgAAAAQAAAALgAAAB0AAAAW\n" +
                "AAAAHQAAACUAAAAfAAAAOQAAAB4AAABKAAAAHQAAAB4AAAAfAAAAHwAAABsAAAAbAAAAHQAAACEA\n" +
                "AAAeAAAAQgAAACAAAABBAAAAGwAAAD4AAAAdAAAARAAAABwAAAAaAAAAGwAAACIAAAAcAAAAGgAA\n" +
                "AB8AAAAhAAAAIAAAADwAAAAcAAAARgAAABsAAAA9AAAAIAAAAEYAAAAfAAAAJwAAACsAAAA3AAAA\n" +
                "KwAAACQAAAAzAAAAOwAAADIAAAApAAAAMwAAADQAAAAzAAAALwAAADYAAAAhAAAANwAAAD0AAAA2\n" +
                "AAAAHgAAAD4AAABAAAAAPAAAAC4AAAA+AAAALgAAAEwAAAAoAAAAPwAAADUAAAA/AAAAJwAAAEkA\n" +
                "AAA1AAAASQAAACkAAABBAAAALgAAAEEAAAA0AAAAQQAAACkAAABDAAAALgAAAEYAAAA0AAAAQwAA\n" +
                "ABAAAAAmAAAAUAAAACUAAAATAAAANgAAAE8AAAA3AAAAAQAAAAEAAAAAAAAAAAAAAAAAAAA=\n");
        msaTList.add(msa);

        msa = new msaT("T-10000000000002", "Tolu Ajayi", "jdshfbyjfhfuhsfuihfiubduh");
        msaTList.add(msa);

        msaAdapter.notifyDataSetChanged();
    }

    public void CallLuxandPrefs(String staff_id, String template) {
        SharedPreferences.Editor editor = myPref.edit();
        editor.putString("msaStaff", staff_id);
        editor.commit();
        Intent LuxandIntent = new Intent(this, LuxandAuthActivity.class);
        LuxandIntent.putExtra("PERSON", staff_id);
        LuxandInfo luxandInfo = new LuxandInfo(this);
        luxandInfo.putTemplate(template);
        luxandInfo.putMessage("DO NOT PRESS BACK UNTIL FACE IS DONE CAPTURING");
        startActivityForResult(LuxandIntent, 419);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 419) {
            if (data.getIntExtra("RESULT", 0) == 1) {
                Message.message(getApplicationContext(), "Face Authenticated");
            } else {
                Message.message(getApplicationContext(), "Face Not Authenticated");
            }
        }
    }
}
