<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class statusPengerjaan extends Model
{
    protected $table= 'status_pengerjaan';
    protected $primaryKey= 'id_status_pengerjaan';
    public $timestamps= true;
    use SoftDeletes;
    protected $fillable= [
        'nama_status_pengerjaan'
    ];
}
