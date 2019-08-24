<!DOCTYPE>
<html>
    <head>
        <title>API</title>
    </head>
    <body>
        <center>
            <h1>Be-loved API</h1> <br> <br>
            
            <div align="left" style="margin-left: 40%">
                <ol>
                    <li> <a href=<?php $data['base_url'] ?>"api/sparepart">sparepart</a> <br> <br> </li>
                    <li> <a href=<?php $data['base_url'] ?>"api/role">role</a> <br> <br> </li>
                    <li> <a href=<?php $data['base_url'] ?>"api/supplier">supplier</a> <br> <br> </li>
                    <li> <a href=<?php $data['base_url'] ?>"api/cabang">cabang</a> <br> <br> </li>
                    <li> <a href=<?php $data['base_url'] ?>"api/jasaService">jasaService</a> <br> <br> </li>
                    <li> <a href=<?php $data['base_url'] ?>"api/kendaraan">kendaraan</a> <br> <br> </li>
                    <li> <a href=<?php $data['base_url'] ?>"api/pelanggan">pelanggan</a> <br> <br> </li>
                    <li> <a href=<?php $data['base_url'] ?>"api/statusPengerjaan">statusPengerjaan</a> <br> <br> </li>
                    <li> <a href=<?php $data['base_url'] ?>"api/pegawai">pegawai</a> <br> <br> </li>
                    
                    <li> <a href=<?php $data['base_url'] ?>"api/pengadaanSparepart">pengadaanSparepart</a> <br> <br> </li>
                    <li> <a href=<?php $data['base_url'] ?>"api/detilPengadaanSparepart">detilPengadaanSparepart</a> <br> <br> </li>
                    <li> <a href=<?php $data['base_url'] ?>"api/transaksi">transaksi</a> <br> <br> </li>
                    <li> <a href=<?php $data['base_url'] ?>"api/transaksiPenjualanJasaService">transaksiPenjualanJasaService</a> <br> <br> </li>
                    <li> <a href=<?php $data['base_url'] ?>"api/transaksiPenjualanSparepart">transaksiPenjualanSparepart</a> <br> <br> </li>
                </ol>
            </div>

            <!-- <form action="{{ route('sparepart.store') }}" method="post" enctype="multipart/form-data">
                <div class="form-group {{ !$errors->has('file') ?: 'has-error' }}">
                    <label>Gambar sparepart</label>
                    <input type="file" name="gambar_sparepart">
                </div><br>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Upload</button>
                </div>
            </form> -->

            <!-- <img src="http://localhost:8000/files/gambar_sparepart/1554993758-p3l-sudansdoaihdaoihsdoixahsdasd.PNG" alt="sparepart"> -->
            
        </center>
    </body>
</html>