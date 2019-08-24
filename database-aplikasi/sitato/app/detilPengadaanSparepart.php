<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class detilPengadaanSparepart extends Model
{
    protected $table= 'detil_pengadaan_sparepart';
    protected $primaryKey= 'id_detil_pengadaan_sparepart';
    public $timestamps= true;
    use SoftDeletes;
    protected $fillable= [
        'id_pengadaan_sparepart',
        'id_sparepart',
        'satuan_detil_pengadaan_sparepart',
        'jumlah_beli_detil_pengadaan_sparepart',
        'subtotal_detil_pengadaan_sparepart'
    ];
}
